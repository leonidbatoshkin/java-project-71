package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import static hexlet.code.Parser.parse;
import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {

    private static Path gendiffPath;
    private static Path stylishFilePath;
    private static Path plainFilePath;
    private static Path jsonFilePath;
    private static File jsonParseFilePath;
    private static File yamlParseFilePath;

    private static Stream<Arguments> jsonPaths() {
        return Stream.of(
                Arguments.of("src/test/resources/big_file1.json", "src/test/resources/big_file2.json"));
    }

    private static Stream<Arguments> yamlPaths() {
        return Stream.of(
                Arguments.of("src/test/resources/big_file1.yml", "src/test/resources/big_file2.yml"));
    }

    private static Stream<Arguments> smallJSONPaths() {
        return Stream.of(
                Arguments.of("src/test/resources/file1.json", "src/test/resources/file2.json"));
    }

    @BeforeAll
    static void init() {
        gendiffPath = Paths.get("src/test/resources/test_gendiff.txt").toAbsolutePath().normalize();
        stylishFilePath = Paths.get("src/test/resources/test_stylish_format.txt").toAbsolutePath().normalize();
        plainFilePath = Paths.get("src/test/resources/test_plain_format.txt").toAbsolutePath().normalize();
        jsonFilePath = Paths.get("src/test/resources/test_json_format.txt").toAbsolutePath().normalize();
        jsonParseFilePath = new File("src/test/resources/file1.json");
        yamlParseFilePath = new File("src/test/resources/yaml1.yml");
    }

    @ParameterizedTest
    @MethodSource("jsonPaths")
    void testGenerateDiffJSON(String filepath1, String filepath2) throws Exception {
        var expected = Files.readString(gendiffPath);
        var actualJSON = generate(filepath1, filepath2);
        assertEquals(expected, actualJSON);
    }

    @ParameterizedTest
    @MethodSource("yamlPaths")
    void testGenerateDiffYAML(String filepath1, String filepath2) throws Exception {
        var expected = Files.readString(gendiffPath);
        var actualYAML = generate(filepath1, filepath2);
        assertEquals(expected, actualYAML);
    }

    @Test
    void testParseJSON() throws Exception {
        var expected = Map.of("host", "hexlet.io", "timeout", Integer.parseInt("50"),
                "proxy", "123.234.53.22",
                "follow", false);
        var actual = parse(Utils.getContent(jsonParseFilePath.getAbsolutePath()), "json");
        assertEquals(expected, actual);
    }

    @Test
    void testParseYAML() throws Exception {
        var expected = Map.of("host", "hexlet.io", "timeout", Integer.parseInt("50"),
                "proxy", "123.234.53.22",
                "follow", false);
        var actual = parse(Utils.getContent(yamlParseFilePath.getAbsolutePath()), "yml");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("smallJSONPaths")
    void testStylishFormat(String filepath1, String filepath2) throws Exception {
        var expected = Files.readString(stylishFilePath);
        var actual = generate(filepath1, filepath2, "stylish");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("yamlPaths")
    void testPlainFormat(String filepath1, String filepath2) throws Exception {
        var expected = Files.readString(plainFilePath);
        var actual = generate(filepath1, filepath2, "plain");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("smallJSONPaths")
    void testJSONFormat(String filepath1, String filepath2) throws Exception {
        var expected = Files.readString(jsonFilePath);
        var actual = generate(filepath1, filepath2, "json");
        assertEquals(expected, actual);
    }
}
