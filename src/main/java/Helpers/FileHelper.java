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

    /**
     * Read all lines from specified file and returns it as a list of Strings
     *
     * @param pathToFile
     * Path where file is placed
     * @return
     * List of file's lines
     */
    public static List<String> readFromFile(String pathToFile) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(ExceptionMessage.getFailedToReadFileMessage(pathToFile));
        }
        return lines;
    }

    /**
     * Checks if file extension is equal to specified
     *
     * @param path
     * Path to file to check
     * @param extension
     * Expected extension
     * @return
     * True if expected extension equals to actual, false if not
     */
    public static boolean checkExtension(String path, String extension) {
        return path.substring(path.lastIndexOf('.') + 1).equalsIgnoreCase(extension);
    }

    /**
     * Checks if given path is valid by creating Path instance
     *
     * @param path
     * Path to file to check
     * @return
     * True if path is valid, false if not
     */
    public static boolean isPathValid(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    /**
     * Gets list of all directories from directory with specified path
     * @param path
     * Path to directory which content to read
     * @return
     * List of directory names
     */
    public static List<String> getDirectories(String path) {
        File[] items = getItems(path);
        return Arrays.stream(items)
                .filter(File::isDirectory)
                .map(File::toString)
                .collect(Collectors.toList());
    }

    /**
     * Gets list of all files from directory with specified path
     * @param path
     * Path to directory which content to read
     * @return
     * List of file names
     */
    public static List<String> getFiles(String path) {
        File[] items = getItems(path);
        return Arrays.stream(items)
                .filter(File::isFile)
                .map(File::toString)
                .collect(Collectors.toList());
    }

    /**
     * Gets list of all items from directory with specified path
     * @param path
     * Path to directory which content to read
     * @return
     * List of item names
     */
    private static File[] getItems(String path) {
        if(path.isEmpty() || !isPathValid(path)) {
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
