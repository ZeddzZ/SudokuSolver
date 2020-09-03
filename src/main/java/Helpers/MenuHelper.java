package Helpers;

import Exceptions.ExceptionMessage;
import Sudoku.SudokuField;
import Sudoku.SudokuSolver;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MenuHelper {

    /**
     * Shows properties from default properties file
     */
    public static void showProperties() {
        Properties props = PropertiesHelper.getProperties();
        int count = 1;
        for (Map.Entry<Object, Object> prop: props.entrySet()) {
            System.out.println(count + ". " + prop.getKey() + " = " + prop.getValue());
            count++;
        }
    }

    /**
     * Edits property that are already loaded (not saving to file)
     */
    public static void editProperty() {
        showProperties();
        System.out.print("Enter name of property to change: ");
        Scanner scanner = new Scanner(System.in);
        String propName = scanner.nextLine();
        if(!PropertiesHelper.containProperty(propName)) {
            System.out.println("No such property exists! Returning...");
            System.out.println();
            return;
        }
        System.out.println("Current value of property " + propName + " is " + PropertiesHelper.getProperty(propName));
        System.out.print("Enter new value for property " + propName + ": ");
        String propValue = scanner.nextLine();
        System.out.println("You are going to change value of property " + propName + " from " + PropertiesHelper.getProperty(propName) + " to " + propValue + ". Are you sure (Yes/No)?");
        String confirmation = scanner.nextLine();
        if (confirmation.trim().equalsIgnoreCase("yes")) {
            PropertiesHelper.setProperty(propName, propValue);
            System.out.println("You have successfully changed value of property " + propName + " from " + PropertiesHelper.getProperty(propName) + " to " + propValue + ".");
        } else {
            System.out.println("No confirmation was received. Reverting this change. Value of property " + propName + " remains " + PropertiesHelper.getProperty(propName) + ".");
        }
    }

    /**
     * Adds new property to properties list (not saving to file)
     */
    public static void addProperty() {
        showProperties();
        System.out.print("Enter name of property to add: ");
        Scanner scanner = new Scanner(System.in);
        String propName = scanner.nextLine();
        System.out.print("Enter value of property to add: ");
        String propValue = scanner.nextLine();
        System.out.println("You are going to add property with name " + propName + " and value " + propValue + ". Are you sure (Yes/No)?");
        String confirmation = scanner.nextLine();
        if (confirmation.trim().equalsIgnoreCase("yes")) {
            PropertiesHelper.setProperty(propName, propValue);
            System.out.println("You have successfully added property with name " + propName + " and value " + propValue + ".");
        } else {
            System.out.println("No confirmation was received. Reverting this change. Property with name " + propName + " and value " + propValue + " was not added.");
        }
    }

    /**
     * Saving current properties to file (default are new one)
     */
    public static void saveProperties() {
        showProperties();
        System.out.println("Default properties file path is " + PropertiesHelper.getPathToProperties());
        System.out.println("Do you want override it or create new file (Override/New)?");
        Scanner scanner = new Scanner(System.in);
        String override = scanner.nextLine();
        if (override.trim().equalsIgnoreCase("override")) {
            System.out.println("You are going to override existing properties file. Are you sure (Yes/No)?");
            String confirmation = scanner.nextLine();
            if (confirmation.trim().equalsIgnoreCase("yes")) {
                PropertiesHelper.saveProperties(PropertiesHelper.getPathToProperties());
                System.out.println("Successfully saved properties to " + PropertiesHelper.getPathToProperties());
            } else {
                System.out.println("No confirmation was received. Cancelling...");
            }
        } else {
            System.out.println("You are going to create new properties file. Enter path:");
            String path = scanner.nextLine();
            if (!FileHelper.isPathValid(path)) {
                System.out.println("Path you entered is invalid. Cancelling...");
            } else {
                System.out.println("You are going to create new properties file with path " + path + ". Are you sure (Yes/No)?");
                String confirmation = scanner.nextLine();
                if (confirmation.trim().equalsIgnoreCase("yes")) {
                    PropertiesHelper.saveProperties(path);
                    System.out.println("Successfully saved properties to " + path);
                } else {
                    System.out.println("No confirmation was received. Cancelling...");
                }
            }

        }

    }

    /**
     * Shows list of files and directories in default folder (took from properties)
     */
    public static void showDefaultDirectoryFiles() {
        showDirectoryItems(PropertiesHelper.getDefaultFilePath());
    }

    /**
     * Shows list of files and directories in specified folder (path will be taken during this method)
     */
    public static void showSpecifiedDirectoryFiles() {
        System.out.print("Please enter path to directory: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        if (!FileHelper.isPathValid(path)) {
           System.out.println(ExceptionMessage.getFailedToValidatePathMessage(path) + " Canceling operation.");
        }
        if(!Files.isDirectory(Paths.get(path))) {
            System.out.println(ExceptionMessage.getFailedToValidateDirectoryMessage(path) + " Canceling operation.");
        }
        showDirectoryItems(path);
    }

    /**
     * Selects file form default folder and solving it
     */
    public static void selectFileFromDefaultDirectory() {
        List<String> files = FileHelper.getFiles(PropertiesHelper.getDefaultFilePath());
        showArrayItems(files);
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select file by its number: ");
        String selected = scanner.nextLine();
        if (!MathHelper.tryParseInt(selected)) {
            System.out.println("Can't parse your answer! Input value should be Integer. Canceling operation.");
        } else {
            int selectedItem = Integer.parseInt(selected);
            if (selectedItem <= 0 || selectedItem > files.size()) {
                System.out.println("Received menu item is out of borders! Please, select value from 1 to " + files.size() + ". Canceling operation.");
            } else {
                readFileAndSolve(files.get(selectedItem - 1));
            }
        }
    }

    /**
     * Selects filefrom specified path (path will be taken during this method) and solving it
     */
    public static void selectFileByPath() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path to the file with " + PropertiesHelper.getDefaultExtension() + " extension: ");
        String path = scanner.nextLine();
        if (!FileHelper.isPathValid(path)) {
            System.out.println(ExceptionMessage.getFailedToValidatePathMessage(path) + " Canceling operation.");
        }
        if(!Files.isRegularFile(Paths.get(path))) {
            System.out.println(ExceptionMessage.getFailedToValidateFileMessage(path) + " Canceling operation.");
        }
        readFileAndSolve(path);
    }

    /**
     * Exiting application with exit code 0
     */
    public static void exitApplication() {
        System.out.println("Exiting application.");
        System.exit(0);
    }

    private static void readFileAndSolve(String path) {
        if(!FileHelper.checkExtension(path, PropertiesHelper.getDefaultExtension())) {
            System.out.println(ExceptionMessage.getWrongFileExtensionMessage(path, PropertiesHelper.getDefaultExtension() + " Canceling operation."));
            return;
        }
        SudokuField<Integer> sudokuField = SudokuFieldHelper.getIntegerFieldFromFile(path);
        System.out.println("Initial field is:");
        System.out.println();
        System.out.println(sudokuField.toString());
        System.out.println("Solving this sudoku...");
        System.out.println();
        Set<SudokuField<Integer>> solutions = SudokuSolver.solve(sudokuField);
        SudokuSolver.printSolutions(solutions);
    }

    private static void showDirectoryItems(String path) {
        List<String> dirs = FileHelper.getDirectories(path);
        List<String> files = FileHelper.getFiles(path);
        System.out.println("Showing content in " + path + " folder:");
        System.out.println();
        System.out.println("Folders: ");
        showArrayItems(dirs);
        System.out.println("Files: ");
        showArrayItems(files);
        System.out.println("Total: " + dirs.size() + " directories and " + files.size() + " files.");
        System.out.println();
    }

    private static<T> void showArrayItems(List<T> array) {
        if (array.size() == 0) {
            System.out.println("--- None ---");
        }
        for(int i = 0; i < array.size(); i++) {
            System.out.println((i + 1) + ". " + array.get(i) + ";");
        }
        System.out.println();
    }
}
