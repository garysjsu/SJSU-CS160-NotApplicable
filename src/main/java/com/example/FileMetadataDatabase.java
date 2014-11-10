package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by garindraprahandono on 11/9/14.
 */
public class FileMetadataDatabase {

    private final Map<Integer, FileMetadata> map = new HashMap<Integer, FileMetadata>();

    public FileMetadata create(String name, long size) {
        int id = 1000 + new Random().nextInt(2000 - 1000 + 1);
        FileMetadata metadata = new FileMetadata(id, name, size);

        map.put(id, metadata);

        return metadata;
    }

    public FileMetadata get(int id) {
        return map.get(id);
    }
}
