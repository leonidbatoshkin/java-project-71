package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.JSONFormatter;
import hexlet.code.formatters.PlainFormatter;

import java.util.List;
import java.util.Map;

public final class Formatter {

    public static String getRepresentation(Map<String, List<Object>> lines, String format)
            throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> StylishFormatter.getRepresentation(lines);
            case "plain" -> PlainFormatter.getRepresentation(lines);
            case "json" -> JSONFormatter.getRepresentation(lines);
            default -> throw new UnsupportedOperationException("This format is not supported");
        };
    }
}
