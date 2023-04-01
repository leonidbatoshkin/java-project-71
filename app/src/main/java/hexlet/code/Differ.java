package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import static java.nio.charset.StandardCharsets.UTF_8;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
class Differ implements Callable<Integer> {

    static String getFile(String filepath) throws IOException {
        return Files.readString(Paths.get(filepath).toAbsolutePath().normalize(), UTF_8);
    }

    static Map<String, String> parse(String file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, new TypeReference<Map<String, String>>() {
        });
    }

    public static String generate(String path1, String path2) throws Exception {
        String result = "{\n";
        Map<String, String> firstMap = parse(getFile(path1));
        Map<String, String> secondMap = parse(getFile(path2));
        Set<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());
        for (String key : keys) {
            if (!firstMap.containsKey(key)) {
                result += "  + " + key + ": " + secondMap.get(key) + "\n";
            } else if (!secondMap.containsKey(key)) {
                result += "  - " + key + ": " + firstMap.get(key) + "\n";
            } else if (firstMap.get(key).equals(secondMap.get(key))) {
                result += "    " + key + ": " + firstMap.get(key) + "\n";
            } else {
                result += "  - " + key + ": " + firstMap.get(key) + "\n"
                        + "  + " + key + ": " + secondMap.get(key) + "\n";
            }
        }
        result += "}";
        return result;
    }

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Parameters(description = "path to first file")
    static String filepath1;
    @Parameters(description = "path to second file")
    static String filepath2;

    @Override
    public Integer call() throws Exception {
        System.out.println(generate(filepath1, filepath2));
        return 0;
    }
}
