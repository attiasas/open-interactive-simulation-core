package org.attias.open.interactive.simulation.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class IOUtils {

    public static ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    // File System utils

    public static boolean copyFile(InputStream src, Path target, boolean failIfCantCreate) throws IOException {
        try {
            Files.copy(src, target);
            return true;
        } catch (Exception e) {
            if (failIfCantCreate){
                throw e;
            }
            return false;
        }
    }

    public static boolean copyFile(Path src, Path target, boolean failIfCantCreate) throws IOException {
        try {
            Files.copy(src, target);
            return true;
        } catch (Exception e) {
            if (failIfCantCreate){
                throw e;
            }
            return false;
        }
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

    public static boolean deleteDirectoryContent(Path directoryPath) {
        File directory = directoryPath.toFile();
        if (!directory.exists() || !directory.isDirectory()) {
            return false;
        }
        return deleteContent(directory);
    }

    private static boolean deleteContent(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return false;
        }
        boolean result = true;
        for (File file : files) {
            if (file.isDirectory()) {
                deleteContent(file);
            }
            result &= file.delete();
        }
        return result;
    }

    public static boolean createFileIfNotExists(Path path) throws IOException {
        File file = path.toFile();
        if (file.exists()) {
            return false;
        }
        return file.createNewFile();
    }

    // ZIP

    public interface ZipEntryConvertor {
        String getEntryNameIfNotFiltered(File item);
    }

    public static void zipItems(Path archiveTargetPath, Path... itemsToZip) {
        zipItems(archiveTargetPath, File::getName,itemsToZip);
    }

    public static void zipItems(Path archiveTargetPath, ZipEntryConvertor itemConvertor, Path... itemsToZip) {
        try(FileOutputStream fos = new FileOutputStream(archiveTargetPath.toString());
            ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            Set<String> addedEntries = new HashSet<>();

            for (Path itemPath : itemsToZip) {
                File item = itemPath.toFile();
                if (item.exists()) {
                    if (item.isDirectory()) {
                        zipDirectory(item, itemConvertor, itemConvertor.getEntryNameIfNotFiltered(item), zipOut, buffer, addedEntries);
                    } else {
                        zipFile(item, itemConvertor, "", zipOut, buffer, addedEntries);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipDirectory(File dir, ZipEntryConvertor itemConvertor, String baseName, ZipOutputStream zipOut, byte[] buffer, Set<String> addedEntries)
            throws IOException {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String dirConvertedName = itemConvertor.getEntryNameIfNotFiltered(file);
                    if (dirConvertedName == null || dirConvertedName.isBlank()) {
                        // Filtered
                        continue;
                    }
                    zipDirectory(file, itemConvertor, baseName + File.separator + dirConvertedName, zipOut, buffer, addedEntries);
                } else {
                    zipFile(file, itemConvertor, baseName, zipOut, buffer, addedEntries);
                }
            }
        }
    }

    private static void zipFile(File file, ZipEntryConvertor itemConvertor, String baseName, ZipOutputStream zipOut, byte[] buffer, Set<String> addedEntries)
            throws IOException {
        String fileConvertedName = itemConvertor.getEntryNameIfNotFiltered(file);
        if (fileConvertedName == null || fileConvertedName.isBlank()) {
            // Filter
            return;
        }
        String entryName = baseName + File.separator + fileConvertedName;
        if (addedEntries.contains(entryName)) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(entryName);
            zipOut.putNextEntry(zipEntry);

            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zipOut.write(buffer, 0, length);
            }
            addedEntries.add(entryName);
        }
    }

    // JSON Utils

    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public static void writeAsJsonFile(Object obj, Path destination) throws IOException {
        createFileIfNotExists(destination);
        objectMapper.writeValue(destination.toFile(), obj);
    }

    public static <T> T getObjFromJsonFile(InputStream in, Class<T> valueType) throws IOException {
        return objectMapper.readValue(in, valueType);
    }

    public static <T> T getObjFromJsonFile(File jsonFile, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonFile, valueType);
    }
}
