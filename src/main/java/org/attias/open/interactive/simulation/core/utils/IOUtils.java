package org.attias.open.interactive.simulation.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class IOUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean createDirIfNotExists(Path dirPath, boolean failIfCantCreate) {
        File dir = dirPath.toFile();
        if (!dir.exists()) {
            boolean created = dir.mkdir();
            if (!created && failIfCantCreate) {
                throw new RuntimeException("Can't create directory at " + dirPath);
            }
            return created;
        }
        return false;
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static void writeAsJsonFile(Object obj, Path destination) throws IOException {
        objectMapper.writeValue(destination.toFile(), obj);
    }

    public static <T> T getObjFromJsonFile(Path jsonFile, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonFile.toFile(), valueType);
    }
}
