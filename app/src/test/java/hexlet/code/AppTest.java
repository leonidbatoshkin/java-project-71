package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.File;
import java.util.Map;

import static hexlet.code.Differ.getFile;
import static hexlet.code.Differ.parse;
import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {
    File filepath;

    @BeforeEach
    void init() {
        filepath = new File("src/test/resources/file1.json");
    }

    @Test
    void testGetFile() throws IOException {
        var file = getFile(filepath.getAbsolutePath());
        assertEquals(file, ("{\n"
                + "  \"host\": \"hexlet.io\",\n"
                + "  \"timeout\": 50,\n"
                + "  \"proxy\": \"123.234.53.22\",\n"
                + "  \"follow\": false\n"
                + "}"));
    }

    @Test
    void testParse() throws Exception {
        var expected = Map.of("timeout", "50", "follow", "false", "host", "hexlet.io",
                "proxy", "123.234.53.22");
        var actual = parse(Differ.getFile(filepath.getAbsolutePath()));
        assertEquals(expected, actual);
    }

    @Test
    void testGenerate() throws Exception {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.json";
        var expected = "{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}";
        var actual = generate(filepath1, filepath2);
        assertEquals(expected, actual);
    }
}
