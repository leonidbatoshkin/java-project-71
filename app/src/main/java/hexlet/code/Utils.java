package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Utils {

    private static final List<String> TYPES = List.of("json", "yml");

    public static boolean checkFilesFormat(String pathToFirstFile, String pathToSecondFile) {
        if (!isTypeSupported(getExtension(pathToFirstFile)) && isTypeSupported(getExtension(pathToSecondFile))) {
            throw new UnsupportedOperationException("Comparison of not JSON or YAML file formats is not supported");
        }
        return true;
    }

    public static boolean isTypeSupported(String extension) {
        return TYPES.contains(extension);
    }

    public static String getExtension(String pathToFile) {
        var pathToFileArray = pathToFile.split("\\.");
        return pathToFileArray[pathToFileArray.length - 1];
    }

    public static String getContent(String filepath) throws IOException {
        return Files.readString(Paths.get(filepath).toAbsolutePath().normalize(), UTF_8);
    }

    public static String processComplexType(Object obj) {
        if (obj instanceof String) {
            return "'" + obj + "'";
        } else if (obj instanceof Map<?, ?> || obj instanceof List<?>) {
            return "[complex value]";
        }
        return String.valueOf(obj);
    }
}
