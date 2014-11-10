package com.example;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

/**
 * Persists file contents on Amazon S3.
 */
public class FileContentsPersistor {

    private final AmazonS3 s3client;
    private final String bucketName;

    public FileContentsPersistor(AmazonS3 s3client, String bucketName) {
        this.s3client = s3client;
        this.bucketName = bucketName;
    }

    public void persistContents(String fileName, InputStream in) {
        s3client.putObject(bucketName, fileName, in, new ObjectMetadata());
    }

    public InputStream getContents(String fileName) {
        return s3client.getObject(bucketName, fileName).getObjectContent();
    }
}
