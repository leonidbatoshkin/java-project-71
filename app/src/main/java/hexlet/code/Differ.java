package hexlet.code;

import java.util.Map;

import static hexlet.code.DiffMaker.generateDiffEntity;
import static hexlet.code.Parser.parse;
import static hexlet.code.Utils.getContent;
import static hexlet.code.Utils.getExtension;
import static hexlet.code.Formatter.getRepresentation;


public final class Differ {
    public static String generate(String path1, String path2, String formatName) throws Exception {
        Map<String, Object> firstMap = parse(getContent(path1), getExtension(path1));
        Map<String, Object> secondMap = parse(getContent(path2), getExtension(path2));
        return getRepresentation(generateDiffEntity(firstMap, secondMap), formatName);
    }

    public static String generate(String path1, String path2) throws Exception {
        return generate(path1, path2, "stylish");
    }
}
