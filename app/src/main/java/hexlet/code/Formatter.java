package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.JSONFormatter;
import hexlet.code.formatters.PlainFormatter;

import java.util.LinkedHashMap;
import java.util.List;

public final class Formatter {
    private final String format;

    public Formatter(String format) {
        this.format = format;
    }

    String getRepresentation(LinkedHashMap<String, List<Object>> lines) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> new StylishFormatter(lines).getRepresentation();
            case "plain" -> new PlainFormatter(lines).getRepresentation();
            case "json" -> new JSONFormatter(lines).getRepresentation();
            default -> throw new UnsupportedOperationException("This format is not supported");
        };
    }
}
