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
import static hexlet.code.Utils.getContent;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {

    private static Path gendiffPath;
    private static Path stylishFilePath;
    private static Path plainFilePath;
    private static Path jsonFilePath;
    private static File jsonParseFilePath;
    private static File yamlParseFilePath;

    private static Stream<Arguments> paths() {
        return Stream.of(
                Arguments.of("src/test/resources/big_file1.json", "src/test/resources/big_file2.json",
                        "src/test/resources/big_file1.yml", "src/test/resources/big_file2.yml",
                        "src/test/resources/file1.json", "src/test/resources/file2.json"));
    }

    private static Stream<Arguments> pathsWithFormat() {
        return Stream.of(
                Arguments.of("src/test/resources/big_file1.yml", "src/test/resources/big_file2.yml", "plain",
                        "src/test/resources/file1.json", "src/test/resources/file2.json", "stylish",
                        "src/test/resources/file1.json", "src/test/resources/file2.json", "json"));
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
    @MethodSource("paths")
    void testGenerateDiffJSON(String filepath1, String filepath2) throws Exception {
        var expected = Files.readString(gendiffPath);
        var actual = generate(filepath1, filepath2);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("pathsWithFormat")
    void testFormat(String filepath1, String filepath2, String formatName) throws Exception {
        String expected = "";
        if (formatName.equals("stylish")) {
            expected = Files.readString(stylishFilePath);
        } else if (formatName.equals("plain")) {
            expected = Files.readString(plainFilePath);
        } else if (formatName.equals("json")) {
            expected = Files.readString(jsonFilePath);
        }
        var actual = generate(filepath1, filepath2, formatName);
        assertEquals(expected, actual);
    }

    @Test
    void testParseJSON() throws Exception {
        var expected = Map.of("host", "hexlet.io", "timeout", Integer.parseInt("50"),
                "proxy", "123.234.53.22",
                "follow", false);
        var actual = parse(getContent(jsonParseFilePath.getAbsolutePath()), "json");
        assertEquals(expected, actual);
    }

    @Test
    void testParseYAML() throws Exception {
        var expected = Map.of("host", "hexlet.io", "timeout", Integer.parseInt("50"),
                "proxy", "123.234.53.22",
                "follow", false);
        var actual = parse(getContent(yamlParseFilePath.getAbsolutePath()), "yml");
        assertEquals(expected, actual);
    }
}
