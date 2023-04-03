package hexlet.code;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import static hexlet.code.Parser.parse;
import static hexlet.code.Utils.getFile;
import static hexlet.code.Utils.getFileFormat;
import static hexlet.code.Utils.checkFilesFormat;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
class Differ implements Callable<Integer> {
    public static String generate(String path1, String path2) throws Exception {
        if (!checkFilesFormat(path1, path2)) {
            throw new UnsupportedOperationException("Comparison of not JSON or YAML file formats isn't supported");
        }
        Map<String, String> firstMap = parse(getFile(path1), getFileFormat(path1));
        Map<String, String> secondMap = parse(getFile(path2), getFileFormat(path2));
        Set<String> keys = new TreeSet<>(firstMap.keySet());
        String result = "{\n";
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
