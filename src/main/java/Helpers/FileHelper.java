package Helpers;

import Exceptions.ExceptionMessage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {

    public static List<String> readFromFile(String pathToFile) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(ExceptionMessage.getFailedToReadFileMessage(pathToFile));
        }
        return lines;
    }

    public static boolean checkExtension(String path, String extension) {
        return path.substring(path.lastIndexOf('.') + 1).equalsIgnoreCase(extension);
    }

    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    public static List<String> getDirectories(String path) {
        File[] items = getItems(path);
        return Arrays.stream(items)
                .filter(File::isDirectory)
                .map(File::toString)
                .collect(Collectors.toList());
    }

    public static List<String> getFiles(String path) {
        File[] items = getItems(path);
        return Arrays.stream(items)
                .filter(File::isFile)
                .map(File::toString)
                .collect(Collectors.toList());
    }

    private static File[] getItems(String path) {
        if(path.isEmpty() || !isValidPath(path)) {
            System.err.println(ExceptionMessage.getFailedToValidatePathMessage(path));
            return new File[0];
        }
        if(!Files.isDirectory(Paths.get(path))) {
            System.err.println(ExceptionMessage.getFailedToValidateDirectoryMessage(path));
            return new File[0];
        }
        return new File(path).listFiles();
    }
}
