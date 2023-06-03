package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {

    private static String genDiff;
    private static String stylishFile;
    private static String plainFile;
    private static String jsonFile;

    private static Stream<Arguments> paths() {
        return Stream.of(
                Arguments.of("src/test/resources/big_file1.json", "src/test/resources/big_file2.json",
                        "src/test/resources/big_file1.yml", "src/test/resources/big_file2.yml"));
    }

    private static Stream<Arguments> pathsWithFormat() {
        return Stream.of(
                Arguments.of("src/test/resources/big_file1.yml", "src/test/resources/big_file2.yml", "plain",
                        plainFile, "src/test/resources/big_file1.json", "src/test/resources/big_file2.json", "stylish",
                        stylishFile, "src/test/resources/big_file1.json", "src/test/resources/big_file2.yml", "json",
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
}
