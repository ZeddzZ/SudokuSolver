package Helpers;

import Exceptions.ExceptionMessage;
import Exceptions.PropertiesInitializeException;

import java.io.*;
import java.util.Properties;

public class PropertiesHelper {
    protected static String pathToProperties = "src/main/resources/config.properties";
    protected static Properties properties;

    public static void initProperties() throws PropertiesInitializeException {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(pathToProperties)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            throw new PropertiesInitializeException(ExceptionMessage.getWrongFilePathMessage(pathToProperties), e);
        } catch (IOException e) {
            throw new PropertiesInitializeException(ExceptionMessage.getFailedToParsePropertiesMessage(pathToProperties), e);
        }
    }

    public static String getPathToProperties() {
        return pathToProperties;
    }

    public static void setPathToProperties(String path) {
        pathToProperties = path;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties newProperties) {
        properties = newProperties;
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName, "");
    }

    public static void setProperty(String propertyName, String value) {
        properties.setProperty(propertyName, value);
    }

    /**
     * Checks if current set of properties contains specified one
     *
     * @param name
     * Name of property to check
     * @return
     * True if property exists, false if not
     */
    public static boolean containProperty(String name) {
        return !getProperty(name).equals("");
    }

    /**
     * Saves current set of properties to file
     *
     * @param path
     * Path to file where to save properties
     */
    public static void saveProperties(String path) {
        File file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println(ExceptionMessage.getFailedToCreateFileMessage(path));
            }
        }
        try (FileWriter fw = new FileWriter(file)) {
            properties.store(fw, "");
        }
        catch (IOException e) {
            System.err.println(ExceptionMessage.getFailedToSaveDataToFileMessage(path));
        }
    }

    public static int getWidth() {
        return getIntProperty("sudoku.default_width");
    }

    public static int getHeight() {
        return getIntProperty("sudoku.default_height");
    }

    public static int getBlockWidth() {
        return getIntProperty("sudoku.default_block_width");
    }

    public static int getBlockHeight() {
        return getIntProperty("sudoku.default_block_height");
    }

    public static String getDefaultFilePath() {
        return getProperty("sudoku.folder");
    }

    public static String getDefaultExtension() {
        return getProperty("sudoku.extension");
    }

    public static int getDefaultSolutionsCount() {
        return getIntProperty("sudoku.default_solutions_count");
    }

    /**
     * Casts property value to int, if possible
     *
     * @param propertyName
     * Name of property to cast to int
     * @return
     * Integer value of property if possible to cast, 0 if not
     */
    private static int getIntProperty(String propertyName) {
        String prop = getProperty(propertyName);
        if (!MathHelper.tryParseInt(prop)) {
            return 0;
        }
        return Integer.parseInt(prop);
    }
}
