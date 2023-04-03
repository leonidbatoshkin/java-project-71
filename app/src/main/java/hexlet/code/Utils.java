package hexlet.code;

import java.util.List;

public class Utils {
    static boolean checkFilesFormat(String pathToFirstFile, String pathToSecondFile) {
        List<String> types = List.of("json", "yml");
        return types.contains(getFileFormat(pathToFirstFile)) && types.contains(getFileFormat(pathToSecondFile))
                && getFileFormat(pathToFirstFile).equalsIgnoreCase(getFileFormat(pathToSecondFile));
    }

    static String getFileFormat(String pathToFile) {
        var pathToFileArray = pathToFile.split("\\.");
        return pathToFileArray[pathToFileArray.length - 1];
    }
}
