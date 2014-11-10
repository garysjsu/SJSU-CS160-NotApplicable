package com.example;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

/**
 * Persists file contents on Amazon S3.
 */
public class FileContentsPersistor {

    private final AmazonS3 s3client;

    public FileContentsPersistor(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public void persistContents(String fileName, InputStream in) {
        s3client.putObject("handoff-dev", fileName, in, new ObjectMetadata());
    }

    public InputStream getContents(String fileName) {
        return null;
    }
}
