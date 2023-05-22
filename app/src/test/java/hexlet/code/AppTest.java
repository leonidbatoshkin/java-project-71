package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import static hexlet.code.Parser.parse;
import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {

    private static String genDiff;
    private static String stylishFile;
    private static String plainFile;
    private static String jsonFile;
    private static String jsonParseFile;
    private static String yamlParseFile;

    private static Stream<Arguments> paths() {
        return Stream.of(
                Arguments.of("src/test/resources/big_file1.json", "src/test/resources/big_file2.json",
                        "src/test/resources/big_file1.yml", "src/test/resources/big_file2.yml",
                        "src/test/resources/file1.json", "src/test/resources/file2.json"));
    }

    private static Stream<Arguments> pathsWithFormat() {
        return Stream.of(
                Arguments.of("src/test/resources/big_file1.yml", "src/test/resources/big_file2.yml", "plain",
                        plainFile, "src/test/resources/file1.json", "src/test/resources/file2.json", "stylish",
                        stylishFile, "src/test/resources/file1.json", "src/test/resources/file2.json", "json",
                        jsonFile));
    }

    private static String readFile(String stringPath) throws IOException {
        Path path = Paths.get(stringPath).toAbsolutePath().normalize();
        return Files.readString(path);
    }

    @BeforeAll
    static void init() throws IOException {
        genDiff = readFile("src/test/resources/test_gendiff.txt");
        stylishFile = readFile("src/test/resources/test_stylish_format.txt");
        plainFile = readFile("src/test/resources/test_plain_format.txt");
        jsonFile = readFile("src/test/resources/test_json_format.txt");
        jsonParseFile = readFile("src/test/resources/file1.json");
        yamlParseFile = readFile("src/test/resources/yaml1.yml");
    }

    @ParameterizedTest
    @MethodSource("paths")
    void testGenerateDiffJSON(String filepath1, String filepath2) throws Exception {
        assertEquals(genDiff, generate(filepath1, filepath2));
    }

    @ParameterizedTest
    @MethodSource("pathsWithFormat")
    void testFormat(String filepath1, String filepath2, String formatName, String fileContent) throws Exception {
        var actual = generate(filepath1, filepath2, formatName);
        assertEquals(fileContent, actual);
    }

    @Test
    void testParseJSON() throws Exception {
        var expected = Map.ofEntries(
                Map.entry("host", "hexlet.io"),
                Map.entry("timeout", Integer.parseInt("50")),
                Map.entry("proxy", "123.234.53.22"),
                Map.entry("follow", false)
        );
        var actual = parse(jsonParseFile, "json");
        assertEquals(expected, actual);
    }

    @Test
    void testParseYAML() throws Exception {
        var expected = Map.ofEntries(
                Map.entry("host", "hexlet.io"),
                Map.entry("timeout", Integer.parseInt("50")),
                Map.entry("proxy", "123.234.53.22"),
                Map.entry("follow", false)
        );
        var actual = parse(yamlParseFile, "yml");
        assertEquals(expected, actual);
    }
}
