package Exceptions;

import java.text.MessageFormat;
import java.util.List;

public class ExceptionMessage {
    protected final static String WRONG_LINEAR_SIZE_MESSAGE =  "{0} should be divisible by block {0}! Current values are: {0} = {1}, block {0} = {2}, {1} % {2} = {3}.";
    protected final static String WRONG_FILED_SIZE_EXCEPTION = "{0} should be more than zero! Current value is {1}.";
    protected final static String WRONG_FILE_PATH = "Failed to find file using path {0}! Please, double-check it.";
    protected final static String WRONG_FILE_EXTENSION = "File should have extension {0} but was {1}. Please, double-check it.";
    protected final static String FAILED_TO_READ_FILE = "Failed to read file with path {0}! Please, double-check it.";
    protected final static String FAILED_TO_PARSE_PROPERTIES = "Failed to parse properties file with path {0}! Please, double-check it.";
    protected final static String FAILED_TO_PARSE_INTEGER = "Failed to parse to Integer value {0}! Replaced with {1}.";
    protected final static String FAILED_TO_CREATE_FILE = "Failed to create file with path {0}!";
    protected final static String FAILED_TO_VALIDATE_PATH = "Path {0} is invalid!. Please, double-check it.";
    protected final static String FAILED_TO_VALIDATE_DIRECTORY = "Path {0} is not a directory!. Please, double-check it.";
    protected final static String FAILED_TO_VALIDATE_FILE = "Path {0} is not a file!. Please, double-check it.";
    protected final static String FAILED_TO_SAVE_DATA_TO_FILE = "Failed to save data to file with path {0}!";
    protected final static String INVALID_FIELD_VALUES = "Field has invalid values! Possible values are {0}.";

    public static String getWrongLinearSizeMessage(String dimension, int dimensionSize, int blockSize, int modulo) {
        return formatMessage(WRONG_LINEAR_SIZE_MESSAGE, dimension, dimensionSize, blockSize, modulo);
    }

    public static String getWrongFieldSizeMessage(String dimension, int dimensionSize) {
        return formatMessage(WRONG_FILED_SIZE_EXCEPTION, dimension, dimensionSize);
    }

    public static String getWrongFilePathMessage(String path) {
        return formatMessage(WRONG_FILE_PATH, path);
    }

    public static String getFailedToParsePropertiesMessage(String path) {
        return formatMessage(FAILED_TO_PARSE_PROPERTIES, path);
    }

    public static String getFailedToParseIntegerMessage(String value, int defaultValue) {
        return formatMessage(FAILED_TO_PARSE_INTEGER, value, defaultValue);
    }

    public static String getFailedToCreateFileMessage(String path) {
        return formatMessage(FAILED_TO_CREATE_FILE, path);
    }

    public static String getFailedToSaveDataToFileMessage(String path) {
        return formatMessage(FAILED_TO_SAVE_DATA_TO_FILE, path);
    }

    public static String getFailedToValidatePathMessage(String path) {
        return formatMessage(FAILED_TO_VALIDATE_PATH, path);
    }

    public static String getFailedToValidateDirectoryMessage(String path) {
        return formatMessage(FAILED_TO_VALIDATE_DIRECTORY, path);
    }

    public static String getFailedToValidateFileMessage(String path) {
        return formatMessage(FAILED_TO_VALIDATE_FILE, path);
    }

    public static String getWrongFileExtensionMessage(String path, String extension) {
        return formatMessage(WRONG_FILE_EXTENSION, extension, path);
    }

    public static String getFailedToReadFileMessage(String path) {
        return formatMessage(FAILED_TO_READ_FILE, path);
    }

    public static String getInvalidFieldValuesMessage(List<Integer> possibleValues) {
        return formatMessage(INVALID_FIELD_VALUES, possibleValues);
    }

    private static String formatMessage(String pattern, Object ... args) {
        return MessageFormat.format(pattern, args);
    }
}
