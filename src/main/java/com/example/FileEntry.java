package com.example;

import java.io.InputStream;

public class FileEntry {

    private final FileContentsPersistor persistor;
    private final FileMetadata metadata;

    public FileEntry(FileContentsPersistor persistor, FileMetadata metadata) {
        this.persistor = persistor;
        this.metadata = metadata;
    }

    public int getId() {
        return getMetadata().getId();
    }

    public FileMetadata getMetadata() {
        return metadata;
    }

    public InputStream getContents() {
        return persistor.getContents("abcd-" + metadata.getId());
    }
}
