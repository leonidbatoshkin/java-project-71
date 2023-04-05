package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.File;
import java.util.Map;

import static hexlet.code.Utils.getFile;
import static hexlet.code.Parser.parse;
import static hexlet.code.Differ.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {

    @Test
    void testGetFile() throws IOException {
        var filepath = new File("src/test/resources/file1.json");
        var file = getFile(filepath.getAbsolutePath());
        assertEquals(file, ("{\n"
                + "  \"host\": \"hexlet.io\",\n"
                + "  \"timeout\": 50,\n"
                + "  \"proxy\": \"123.234.53.22\",\n"
                + "  \"follow\": false\n"
                + "}"));
    }

    @Test
    void testParseJSON() throws Exception {
        var filepath = new File("src/test/resources/file1.json");
        var expected = Map.of("host", "hexlet.io", "timeout", Integer.parseInt("50"), "proxy", "123.234.53.22",
                "follow", false);
        var actual = parse(Utils.getFile(filepath.getAbsolutePath()), "json");
        assertEquals(expected, actual);
    }

    @Test
    void testParseYAML() throws Exception {
        var filepath = new File("src/test/resources/yaml1.yml");
        var expected = Map.of("host", "hexlet.io", "timeout", Integer.parseInt("50"), "proxy", "123.234.53.22",
                "follow", false);
        var actual = parse(Utils.getFile(filepath.getAbsolutePath()), "yml");
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateDiffJSON() throws Exception {
        String filepath1 = "src/test/resources/big_file1.json";
        String filepath2 = "src/test/resources/big_file2.json";
        var expected = "{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n"
                + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}";
        var actual = generate(filepath1, filepath2);
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateDiffYAML() throws Exception {
        String filepath1 = "src/test/resources/big_file1.yml";
        String filepath2 = "src/test/resources/big_file2.yml";
        var expected = "{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n"
                + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}";
        var actual = generate(filepath1, filepath2);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormat() throws Exception {
        String filepath1 = "src/test/resources/big_file1.yml";
        String filepath2 = "src/test/resources/big_file2.yml";
        var expected = "Property 'chars2' was updated. From [complex value] to false\n"
                + "Property 'checked' was updated. From false to true\n"
                + "Property 'default' was updated. From null to [complex value]\n"
                + "Property 'id' was updated. From 45 to null\n"
                + "Property 'key1' was removed\n"
                + "Property 'key2' was added with value: 'value2'\n"
                + "Property 'numbers2' was updated. From [complex value] to [complex value]\n"
                + "Property 'numbers3' was removed\n"
                + "Property 'numbers4' was added with value: [complex value]\n"
                + "Property 'obj1' was added with value: [complex value]\n"
                + "Property 'setting1' was updated. From 'Some value' to 'Another value'\n"
                + "Property 'setting2' was updated. From 200 to 300\n"
                + "Property 'setting3' was updated. From true to 'none'";
        var actual = generate(filepath1, filepath2, "plain");
        assertEquals(expected, actual);
    }

    @Test
    void testJSONFormat() throws Exception {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.json";
        var expected = "{\"follow\":[\"deleted\",false],\"host\":[\"unchanged\",\"hexlet.io\"],\"proxy\":[\"deleted\","
                + "\"123.234.53.22\"],\"timeout\":[\"changed\",50,20],\"verbose\":[\"added\",true]}";
        var actual = generate(filepath1, filepath2, "json");
        assertEquals(expected, actual);
    }
}
