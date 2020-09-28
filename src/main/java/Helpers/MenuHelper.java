package Helpers;

import Exceptions.ExceptionMessage;
import Loggers.IShower;
import Sudoku.SudokuField;
import Sudoku.SudokuSolver;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MenuHelper {

    /**
     * Shows properties from default properties file
     */
    public static void showProperties(IShower menuShower) {
        Properties props = PropertiesHelper.getProperties();
        int count = 1;
        for (Map.Entry<Object, Object> prop: props.entrySet()) {
            menuShower.show(count + ". " + prop.getKey() + " = " + prop.getValue());
            count++;
        }
    }

    /**
     * Edits property that are already loaded (not saving to file)
     */
    public static void editProperty(IShower menuShower) {
        showProperties(menuShower);
        System.out.print("Enter name of property to change: ");
        Scanner scanner = new Scanner(System.in);
        String propName = scanner.nextLine();
        if(!PropertiesHelper.containProperty(propName)) {
            menuShower.show("No such property exists! Returning...");
            menuShower.show();
            return;
        }
        menuShower.show("Current value of property " + propName + " is " + PropertiesHelper.getProperty(propName));
        menuShower.show("Enter new value for property " + propName + ": ");
        String propValue = scanner.nextLine();
        menuShower.show("You are going to change value of property " + propName + " from " + PropertiesHelper.getProperty(propName) + " to " + propValue + ". Are you sure (Yes/No)?");
        String confirmation = scanner.nextLine();
        if (confirmation.trim().equalsIgnoreCase("yes")) {
            PropertiesHelper.setProperty(propName, propValue);
            menuShower.show("You have successfully changed value of property " + propName + " from " + PropertiesHelper.getProperty(propName) + " to " + propValue + ".");
        } else {
            menuShower.show("No confirmation was received. Reverting this change. Value of property " + propName + " remains " + PropertiesHelper.getProperty(propName) + ".");
        }
    }

    /**
     * Adds new property to properties list (not saving to file)
     */
    public static void addProperty(IShower menuShower) {
        showProperties(menuShower);
        menuShower.show("Enter name of property to add: ");
        Scanner scanner = new Scanner(System.in);
        String propName = scanner.nextLine();
        menuShower.show("Enter value of property to add: ");
        String propValue = scanner.nextLine();
        menuShower.show("You are going to add property with name " + propName + " and value " + propValue + ". Are you sure (Yes/No)?");
        String confirmation = scanner.nextLine();
        if (confirmation.trim().equalsIgnoreCase("yes")) {
            PropertiesHelper.setProperty(propName, propValue);
            menuShower.show("You have successfully added property with name " + propName + " and value " + propValue + ".");
        } else {
            menuShower.show("No confirmation was received. Reverting this change. Property with name " + propName + " and value " + propValue + " was not added.");
        }
    }

    /**
     * Saving current properties to file (default are new one)
     */
    public static void saveProperties(IShower menuShower) {
        showProperties(menuShower);
        menuShower.show("Default properties file path is " + PropertiesHelper.getPathToProperties());
        menuShower.show("Do you want override it or create new file (Override/New)?");
        Scanner scanner = new Scanner(System.in);
        String override = scanner.nextLine();
        if (override.trim().equalsIgnoreCase("override")) {
            menuShower.show("You are going to override existing properties file. Are you sure (Yes/No)?");
            String confirmation = scanner.nextLine();
            if (confirmation.trim().equalsIgnoreCase("yes")) {
                PropertiesHelper.saveProperties(PropertiesHelper.getPathToProperties());
                menuShower.show("Successfully saved properties to " + PropertiesHelper.getPathToProperties());
            } else {
                menuShower.show("No confirmation was received. Cancelling...");
            }
        } else {
            menuShower.show("You are going to create new properties file. Enter path:");
            String path = scanner.nextLine();
            if (!FileHelper.isPathValid(path)) {
                menuShower.show("Path you entered is invalid. Cancelling...");
            } else {
                menuShower.show("You are going to create new properties file with path " + path + ". Are you sure (Yes/No)?");
                String confirmation = scanner.nextLine();
                if (confirmation.trim().equalsIgnoreCase("yes")) {
                    PropertiesHelper.saveProperties(path);
                    menuShower.show("Successfully saved properties to " + path);
                } else {
                    menuShower.show("No confirmation was received. Cancelling...");
                }
            }

        }

    }

    /**
     * Shows list of files and directories in default folder (took from properties)
     */
    public static void showDefaultDirectoryFiles(IShower menuShower) {
        showDirectoryItems(PropertiesHelper.getDefaultFilePath(), menuShower);
    }

    /**
     * Shows list of files and directories in specified folder (path will be taken during this method)
     */
    public static void showSpecifiedDirectoryFiles(IShower menuShower) {
        menuShower.show("Please enter path to directory: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        if (!FileHelper.isPathValid(path)) {
            menuShower.show(ExceptionMessage.getFailedToValidatePathMessage(path) + " Canceling operation.");
        }
        if(!Files.isDirectory(Paths.get(path))) {
            menuShower.show(ExceptionMessage.getFailedToValidateDirectoryMessage(path) + " Canceling operation.");
        }
        showDirectoryItems(path, menuShower);
    }

    /**
     * Selects file form default folder and solving it
     */
    public static void selectFileFromDefaultDirectory(IShower menuShower) {
        List<String> files = FileHelper.getFiles(PropertiesHelper.getDefaultFilePath());
        showArrayItems(files);
        menuShower.show();
        Scanner scanner = new Scanner(System.in);
        menuShower.show("Select file by its number: ");
        String selected = scanner.nextLine();
        if (!MathHelper.tryParseInt(selected)) {
            menuShower.show("Can't parse your answer! Input value should be Integer. Canceling operation.");
        } else {
            int selectedItem = Integer.parseInt(selected);
            if (selectedItem <= 0 || selectedItem > files.size()) {
                menuShower.show("Received menu item is out of borders! Please, select value from 1 to " + files.size() + ". Canceling operation.");
            } else {
                readFileAndSolve(files.get(selectedItem - 1), menuShower);
            }
        }
    }

    /**
     * Selects file from specified path (path will be taken during this method) and solving it
     */
    public static void selectFileByPath(IShower menuShower) {
        Scanner scanner = new Scanner(System.in);
        menuShower.show("Enter path to the file with " + PropertiesHelper.getDefaultExtension() + " extension: ");
        String path = scanner.nextLine();
        if (!FileHelper.isPathValid(path)) {
            menuShower.show(ExceptionMessage.getFailedToValidatePathMessage(path) + " Canceling operation.");
        }
        if(!Files.isRegularFile(Paths.get(path))) {
            menuShower.show(ExceptionMessage.getFailedToValidateFileMessage(path) + " Canceling operation.");
        }
        readFileAndSolve(path, menuShower);
    }

    /**
     * Exiting application with exit code 0
     */
    public static void exitApplication(IShower menuShower) {
        menuShower.show("Exiting application.");
        System.exit(0);
    }

    private static void readFileAndSolve(String path, IShower menuShower) {
        if(!FileHelper.checkExtension(path, PropertiesHelper.getDefaultExtension())) {
            menuShower.show(ExceptionMessage.getWrongFileExtensionMessage(path, PropertiesHelper.getDefaultExtension() + " Canceling operation."));
            return;
        }
        SudokuField<Integer> sudokuField = SudokuFieldHelper.getIntegerFieldFromFile(path);
        menuShower.show("Initial field is:");
        menuShower.show();
        menuShower.show(sudokuField.toString());
        menuShower.show("Solving this sudoku...");
        menuShower.show();
        Set<SudokuField<Integer>> solutions = SudokuSolver.solve(sudokuField);
        menuShower.show(SudokuSolver.printSolutions(solutions));
    }

    private static void showDirectoryItems(String path, IShower menuShower) {
        List<String> dirs = FileHelper.getDirectories(path);
        List<String> files = FileHelper.getFiles(path);
        menuShower.show("Showing content in " + path + " folder:");
        menuShower.show();
        menuShower.show("Folders: ");
        menuShower.show(showArrayItems(dirs));
        menuShower.show("Files: ");
        menuShower.show(showArrayItems(files));
        menuShower.show("Total: " + dirs.size() + " directories and " + files.size() + " files.");
        menuShower.show();
    }

    private static<T> String showArrayItems(List<T> array) {
        StringBuilder sb = new StringBuilder();
        if (array.size() == 0) {
            sb.append("--- None ---").append(System.lineSeparator());
        }
        for(int i = 0; i < array.size(); i++) {
            sb.append(i + 1).append(". ").append(array.get(i)).append(";").append(System.lineSeparator());
        }
        return sb.append(System.lineSeparator()).toString();
    }
}
