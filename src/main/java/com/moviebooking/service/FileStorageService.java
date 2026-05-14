package com.moviebooking.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStorageService {
    private static final Path DATA_DIRECTORY = Paths.get("src", "main", "resources", "data");
    private static final String[] REQUIRED_FILES = {"users.txt", "movies.txt", "bookings.txt"};

    static {
        ensureDataFiles();
    }

    private FileStorageService() {
    }

    // Data is cached in memory and persisted to TXT files for assignment requirements.
    public static List<String> readLines(String fileName) {
        Path file = resolveDataFile(fileName);
        try {
            return Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read data file: " + fileName, e);
        }
    }

    public static void writeLines(String fileName, List<String> lines) {
        Path file = resolveDataFile(fileName);
        try {
            Files.write(file, lines == null ? new ArrayList<>() : lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write data file: " + fileName, e);
        }
    }

    public static void appendLine(String fileName, String line) {
        Path file = resolveDataFile(fileName);
        try {
            List<String> lines = readLines(fileName);
            lines.add(line == null ? "" : line);
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to append data file: " + fileName, e);
        }
    }

    private static Path resolveDataFile(String fileName) {
        ensureDataFiles();
        Path file = DATA_DIRECTORY.resolve(fileName);
        ensureFile(file);
        return file;
    }

    private static void ensureDataFiles() {
        try {
            Files.createDirectories(DATA_DIRECTORY);
            for (String fileName : REQUIRED_FILES) {
                ensureFile(DATA_DIRECTORY.resolve(fileName));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to initialize TXT data storage.", e);
        }
    }

    private static void ensureFile(Path file) {
        try {
            if (Files.notExists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create data file: " + file.getFileName(), e);
        }
    }
}
