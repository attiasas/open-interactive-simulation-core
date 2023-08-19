package org.attias.open.interactive.simulation.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class IOUtils {

    public static ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    public static boolean createDirIfNotExists(Path dirPath, boolean failIfCantCreate) {
        File dir = dirPath.toFile();
        if (dir.exists()) {
            return false;
        }
        boolean created = dir.mkdir();
        if (!created && failIfCantCreate) {
            throw new RuntimeException("Can't create directory at " + dirPath);
        }
        return created;
    }

    public static void copyDirectoryContent(Path sourcePath, Path targetPath) throws IOException {
        // Copy all files and subdirectories from source to target
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(sourcePath, options, Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = targetPath.resolve(sourcePath.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, targetPath.resolve(sourcePath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static boolean createFileIfNotExists(Path path) throws IOException {
        File file = path.toFile();
        if (file.exists()) {
            return false;
        }
        return file.createNewFile();
    }

    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public static void writeAsJsonFile(Object obj, Path destination) throws IOException {
        createFileIfNotExists(destination);
        objectMapper.writeValue(destination.toFile(), obj);
    }

    public static <T> T getObjFromJsonFile(File jsonFile, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonFile, valueType);
    }
}
