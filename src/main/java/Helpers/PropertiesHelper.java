package Helpers;

import Exceptions.ExceptionMessage;
import Exceptions.PropertiesInitializeException;

import java.io.*;
import java.util.Properties;

public class PropertiesHelper {
    protected static String pathToProperties = "src/main/resources/config.properties";
    protected static Properties properties;

    public static void initProperties() throws IOException {
        FileInputStream fis = null;
        properties = new Properties();
        try {
            fis = new FileInputStream(pathToProperties);
            properties.load(fis);
        }
        catch (FileNotFoundException e) {
            throw new PropertiesInitializeException(ExceptionMessage.getWrongFilePathMessage(pathToProperties), e);
        }
        catch (IOException e) {
            throw new PropertiesInitializeException(ExceptionMessage.getFailedToParsePropertiesMessage(pathToProperties), e);
        }
        finally {
            if(fis != null) {
                fis.close();
            }
        }
    }

    public static String getPathToProperties() {
        return pathToProperties;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static boolean containProperty(String name) {
        return !getProperty(name).equals("");
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName, "");
    }

    public static void setProperties(Properties newProperties) {
        properties = newProperties;
    }

    public static void setProperties(String propertyName, String value) {
        properties.setProperty(propertyName, value);
    }

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

    private static int getIntProperty(String propertyName) {
        String prop = getProperty(propertyName);
        if (!MathHelper.tryParseInt(prop)) {
            return 0;
        }
        return Integer.parseInt(prop);
    }
}
