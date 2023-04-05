package hexlet.code;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;


import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import static hexlet.code.Parser.parse;
import static hexlet.code.Utils.getFile;
import static hexlet.code.Utils.checkFilesFormat;
import static hexlet.code.Utils.getFileFormat;
import static hexlet.code.Utils.convertNullValuesToString;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public final class Differ implements Callable<Integer> {
    public static String generate(String path1, String path2, String formatName) throws Exception {
        if (!checkFilesFormat(path1, path2)) {
            throw new UnsupportedOperationException("Comparison of not JSON or YAML file formats isn't supported");
        }
        Map<String, Object> firstMap = convertNullValuesToString(parse(getFile(path1), getFileFormat(path1)));
        Map<String, Object> secondMap = convertNullValuesToString(parse(getFile(path2), getFileFormat(path2)));
        LinkedHashMap<String, List<Object>> dict = new LinkedHashMap<>();
        Formatter formatter = new Formatter(formatName);
        Set<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());
        for (String key : keys) {
            if (!firstMap.containsKey(key)) {
                dict.put(key, List.of("added", secondMap.get(key)));
            } else if (!secondMap.containsKey(key)) {
                dict.put(key, List.of("deleted", firstMap.get(key)));
            } else if (firstMap.get(key).equals(secondMap.get(key))) {
                dict.put(key, List.of("unchanged", firstMap.get(key)));
            } else {
                dict.put(key, List.of("changed", firstMap.get(key), secondMap.get(key)));
            }
        }
        return formatter.getRepresentation(dict);
    }

    public static String generate(String path1, String path2) throws Exception {
        if (!checkFilesFormat(path1, path2)) {
            throw new UnsupportedOperationException("Comparison of not JSON or YAML file formats isn't supported");
        }
        Map<String, Object> firstMap = convertNullValuesToString(parse(getFile(path1), getFileFormat(path1)));
        Map<String, Object> secondMap = convertNullValuesToString(parse(getFile(path2), getFileFormat(path2)));
        LinkedHashMap<String, List<Object>> dict = new LinkedHashMap<>();
        Formatter formatter = new Formatter("stylish");
        Set<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());
        for (String key : keys) {
            if (!firstMap.containsKey(key)) {
                dict.put(key, List.of("added", secondMap.get(key)));
            } else if (!secondMap.containsKey(key)) {
                dict.put(key, List.of("deleted", firstMap.get(key)));
            } else if (firstMap.get(key).equals(secondMap.get(key))) {
                dict.put(key, List.of("unchanged", firstMap.get(key)));
            } else {
                dict.put(key, List.of("changed", firstMap.get(key), secondMap.get(key)));
            }
        }
        return formatter.getRepresentation(dict);
    }

    @Option(names = {"-f", "--format"}, defaultValue = "stylish", description = "output format [default: stylish]")
    private static String format;

    @Parameters(description = "path to first file")
    private static String filepath1;
    @Parameters(description = "path to second file")
    private static String filepath2;

    @Override
    public Integer call() throws Exception {
        System.out.println(generate(filepath1, filepath2, format));
        return 0;
    }
}
