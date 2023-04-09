package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static hexlet.code.Utils.getContent;
import static hexlet.code.Parser.parse;
import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {

    @Test
    void testGetContent() throws IOException {
        Path path = Paths.get("src/test/resources/test_content.txt").toAbsolutePath().normalize();
        var filepath = new File("src/test/resources/file1.json");
        var expected = Files.readString(path);
        var actual = getContent(filepath.getAbsolutePath());
        assertEquals(expected, actual);
    }

    @Test
    void testParseJSON() throws Exception {
        var filepath = new File("src/test/resources/file1.json");
        var expected = Map.of("host", "hexlet.io", "timeout", Integer.parseInt("50"),
                "proxy", "123.234.53.22",
                "follow", false);
        var actual = parse(Utils.getContent(filepath.getAbsolutePath()), "json");
        assertEquals(expected, actual);
    }

    @Test
    void testParseYAML() throws Exception {
        var filepath = new File("src/test/resources/yaml1.yml");
        var expected = Map.of("host", "hexlet.io", "timeout", Integer.parseInt("50"),
                "proxy", "123.234.53.22",
                "follow", false);
        var actual = parse(Utils.getContent(filepath.getAbsolutePath()), "yml");
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateDiff() throws Exception {
        String filepath1 = "src/test/resources/big_file1.json";
        String filepath2 = "src/test/resources/big_file2.json";
        String filepath3 = "src/test/resources/big_file1.yml";
        String filepath4 = "src/test/resources/big_file2.yml";
        Path path = Paths.get("src/test/resources/test_gendiff.txt").toAbsolutePath().normalize();
        var expected = Files.readString(path);
        var actualJSON = generate(filepath1, filepath2);
        var actualYAML = generate(filepath3, filepath4);
        assertEquals(expected, actualJSON);
        assertEquals(expected, actualYAML);
    }

    @Test
    void testPlainFormat() throws Exception {
        String filepath1 = "src/test/resources/big_file1.yml";
        String filepath2 = "src/test/resources/big_file2.yml";
        Path path = Paths.get("src/test/resources/test_plain_format.txt").toAbsolutePath().normalize();
        var expected = Files.readString(path);
        var actual = generate(filepath1, filepath2, "plain");
        assertEquals(expected, actual);
    }

    @Test
    void testJSONFormat() throws Exception {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.json";
        Path path = Paths.get("src/test/resources/test_json_format.txt").toAbsolutePath().normalize();
        var expected = Files.readString(path);
        var actual = generate(filepath1, filepath2, "json");
        assertEquals(expected, actual);
    }
}
