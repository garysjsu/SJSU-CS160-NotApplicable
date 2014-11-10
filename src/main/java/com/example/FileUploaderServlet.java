package com.example;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUploaderServlet extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream out = response.getOutputStream();

        if (!ServletFileUpload.isMultipartContent(request)) {
            out.println("Fail");
            return;
        }

        List<FileItem> items = null;

        try {
            items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

        FileItem uploadedFile = items.get(0);

        if (uploadedFile.getSize() == 0) {
            out.println("Please upload a file.");
            return;
        }

        FileDatabase fileDatabase = FileDatabase.getInstance();
        FileEntry fileEntry = fileDatabase.storeFile(uploadedFile);

        // Redirect to share page.
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", "share_file.jsp?id=" + fileEntry.getId());
    }
}
