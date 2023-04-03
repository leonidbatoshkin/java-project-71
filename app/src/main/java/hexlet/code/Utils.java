package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
}
