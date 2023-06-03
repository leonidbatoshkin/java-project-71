package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Utils {

    public static String getExtension(String pathToFile) {
        var pathToFileArray = pathToFile.split("\\.");
        return pathToFileArray[pathToFileArray.length - 1];
    }

    public static String getContent(String filepath) throws IOException {
        return Files.readString(Paths.get(filepath).toAbsolutePath().normalize(), UTF_8);
    }
}
