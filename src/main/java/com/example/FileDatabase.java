package com.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by garindraprahandono on 11/8/14.
 */
public class FileDatabase {

    private final FileContentsPersistor persistor;
    private final MetadataDatabase metadataDatabase;
    private static FileDatabase instance;

    public FileDatabase(FileContentsPersistor persistor, MetadataDatabase metadataDatabase) {
        this.persistor = persistor;
        this.metadataDatabase = metadataDatabase;
    }

    public int storeFile(FileItem fileItem) {

        int fileId = 1000 + new Random().nextInt(2000 - 1000 + 1);
        String fileName = "abcd-" + fileId;

        try {
            persistor.persistContents(fileName, fileItem.getInputStream());
        } catch (IOException e) {
            System.out.println("Error" + e);
        }

        return fileId;
    }

    public static FileDatabase getInstance() {

        if (instance != null)
            return instance;

        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3client = new AmazonS3Client(credentials);
        FileContentsPersistor persistor = new FileContentsPersistor(s3client);
        MetadataDatabase metadataDatabase = new MetadataDatabase();

        FileDatabase fileDatabase = new FileDatabase(persistor, metadataDatabase);
        instance = fileDatabase;

        return fileDatabase;
    }
}
