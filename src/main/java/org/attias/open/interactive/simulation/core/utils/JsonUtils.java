package org.attias.open.interactive.simulation.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class JsonUtils {

    public static ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public static boolean writeAsJsonFile(Object obj, Path destination) throws IOException {
        boolean created = IOUtils.createFileIfNotExists(destination);
        objectMapper.writeValue(destination.toFile(), obj);
        return created;
    }

    public static <T> T getObjFromJsonFile(InputStream in, Class<T> valueType) throws IOException {
        return objectMapper.readValue(in, valueType);
    }

    public static <T> T getObjFromJsonFile(File jsonFile, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonFile, valueType);
    }

}
