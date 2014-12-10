package com.example;

/**
 * Stores file metadata.
 */
public class FileMetadata {

    private final int id;
    private final String name;
    private final long size;
    private final String uploaderId;

    public FileMetadata(int id, String name, long size, String uploaderId) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.uploaderId = uploaderId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public long getSize() {
		return size;
	}

    public String getUploaderId() {
        return uploaderId;
    }

    @Override
    public String toString() {
    	return "ID: " + id + ", Name: " + name + ", Size: " + size;

    }
}
