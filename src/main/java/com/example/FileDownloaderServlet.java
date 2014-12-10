package com.example;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream out = response.getOutputStream();

        int fileId = new Integer(request.getParameter("id"));

        FileDatabase fileDatabase = FileDatabase.getInstance();

        FileEntry fileEntry = fileDatabase.getFileEntry(fileId);
        InputStream contentsIn = fileEntry.getContents();
        FileMetadata metadata  = fileEntry.getMetadata();

        response.setHeader("Content-Disposition", "attachment; filename=\"" + metadata.getName() + "\"");

        IOUtils.copy(contentsIn, out);
        contentsIn.close();

        String userAgent = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();

        DownloadTracker.getInstance().trackDownload(fileId, userAgent, ipAddress);
    }
}
