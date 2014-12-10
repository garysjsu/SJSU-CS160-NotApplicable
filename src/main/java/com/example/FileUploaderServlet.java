package com.example;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

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

        HttpSession session = request.getSession(true);
        String uploaderIdentifier;

        if (session.getAttribute("uploader_identifier") == null) {
            uploaderIdentifier = "abc123";
            session.setAttribute("uploader_identifier", uploaderIdentifier);
        } else {
            uploaderIdentifier = (String) session.getAttribute("uploader_identifier");
        }

        FileDatabase fileDatabase = FileDatabase.getInstance();
        FileEntry fileEntry = fileDatabase.storeFile(uploaderIdentifier, uploadedFile);

        // Redirect to share page.
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", "share_file.jsp?id=" + fileEntry.getId());
    }
}
