package com.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Main abstraction of file-storing.
 */
public class FileDatabase {

    private final FileContentsPersistor persistor;
    private final FileMetadataDatabase fileMetadataDatabase;
    private static FileDatabase instance;

    public FileDatabase(FileContentsPersistor persistor, FileMetadataDatabase fileMetadataDatabase) {
        this.persistor = persistor;
        this.fileMetadataDatabase = fileMetadataDatabase;
    }

    public FileEntry storeFile(FileItem fileItem) {

        String fileName = fileItem.getName();
        long fileSize = fileItem.getSize();

        FileMetadata metadata = fileMetadataDatabase.add(fileName, fileSize);

        try {
            persistor.persistContents("abcd-" + metadata.getId(), fileItem.getInputStream());
        } catch (IOException e) {
            System.out.println("Error" + e);
        }

        return new FileEntry(persistor, metadata);
    }

    public FileEntry getFileEntry(int fileId) {
        return new FileEntry(persistor, fileMetadataDatabase.getData(fileId));
    }

    public static FileDatabase getInstance() {

        if (instance != null)
            return instance;

        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3client = new AmazonS3Client(credentials);
        FileContentsPersistor persistor = new FileContentsPersistor(s3client, "handoff-dev");
        FileMetadataDatabase fileMetadataDatabase = new FileMetadataDatabase();

        FileDatabase fileDatabase = new FileDatabase(persistor, fileMetadataDatabase);
        instance = fileDatabase;

        return fileDatabase;
    }
}
