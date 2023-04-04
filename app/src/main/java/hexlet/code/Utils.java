package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Utils {
    static boolean checkFilesFormat(String pathToFirstFile, String pathToSecondFile) {
        List<String> types = List.of("json", "yml");
        return types.contains(getFileFormat(pathToFirstFile)) && types.contains(getFileFormat(pathToSecondFile));
    }

    static String getFileFormat(String pathToFile) {
        var pathToFileArray = pathToFile.split("\\.");
        return pathToFileArray[pathToFileArray.length - 1];
    }

    static String getFile(String filepath) throws IOException {
        return Files.readString(Paths.get(filepath).toAbsolutePath().normalize(), UTF_8);
    }

    static Map<String, Object> convertNullValuesToString(Map<String, Object> map) {
        return map.entrySet().stream().
                collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() == null ? "null" : entry.getValue()));
    }

    public static String processComplexType(Object obj) {
        if (obj instanceof String && !obj.equals("null")) {
            return "'" + obj + "'";
        }
        return obj instanceof Map<?, ?> || obj instanceof List<?> ? "[complex value]" : obj.toString();
    }
}
