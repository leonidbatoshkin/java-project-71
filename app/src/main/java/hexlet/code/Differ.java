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
import static hexlet.code.Utils.getFileFormat;
import static hexlet.code.Utils.mapToString;
import static hexlet.code.Utils.checkFilesFormat;
import static hexlet.code.Utils.getFile;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
class Differ implements Callable<Integer> {
    public static String generate(String path1, String path2) throws Exception {
        if (!checkFilesFormat(path1, path2)) {
            throw new UnsupportedOperationException("Comparison of not JSON or YAML file formats isn't supported");
        }
        Map<String, Object> firstMap = parse(getFile(path1), getFileFormat(path1));
        Map<String, Object> secondMap = parse(getFile(path2), getFileFormat(path2));
        LinkedHashMap<String, List<String>> dict = new LinkedHashMap<>();
        Formatter formatter = new Formatter(format);
        Set<String> keys = new TreeSet<>(firstMap.keySet());
        keys.addAll(secondMap.keySet());
        for (String key : keys) {
            if (!firstMap.containsKey(key)) {
                dict.put(key, List.of("added", mapToString(secondMap.get(key))));
            } else if (!secondMap.containsKey(key)) {
                dict.put(key, List.of("deleted", mapToString(firstMap.get(key))));
            } else if (mapToString(firstMap.get(key)).equals(mapToString(secondMap.get(key)))) {
                dict.put(key, List.of("unchanged", mapToString(firstMap.get(key))));
            } else {
                dict.put(key, List.of("changed", mapToString(firstMap.get(key)), mapToString(secondMap.get(key))));
            }
        }
        return formatter.getRepresentation(dict);
    }

    @Option(names = {"-f", "--format"}, defaultValue = "stylish", description = "output format [default: stylish]")
    private static String format = "stylish";

    @Parameters(description = "path to first file")
    private static String filepath1;
    @Parameters(description = "path to second file")
    private static String filepath2;

    @Override
    public Integer call() throws Exception {
        System.out.println(generate(filepath1, filepath2));
        return 0;
    }
}
