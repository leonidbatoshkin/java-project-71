package hexlet.code;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class Formatter {

    private static final int OPERATION_POSITION = 0;
    private static final int VALUE_POSITION = 1;
    private static final int CHANGED_VALUE_POSITION = 2;
    private final String format;
    private final StringBuilder resultString;

    public void setResultString(String line) {
        this.resultString.append(line);
    }

    public Formatter(String format, StringBuilder resultString) {
        this.format = format;
        this.resultString = resultString;
    }

    public Formatter(String format) {
        this.format = format;
        this.resultString = new StringBuilder();
    }

    String getRepresentation(LinkedHashMap<String, List<String>> lines) {
        return switch (this.format) {
            case "stylish" -> getStylishRepresentation(lines);
            case "plain" -> getPlainRepresentation(lines);
            case "json" -> getJSONRepresentation(lines);
            default -> throw new UnsupportedOperationException("This format is not supported");
        };
    }

    private String getStylishRepresentation(LinkedHashMap<String, List<String>> lines) {
        setResultString("{\n");
        Set<Map.Entry<String, List<String>>> entries = lines.entrySet();
        entries.forEach(entry -> {
            var key = entry.getKey();
            var value = entry.getValue();
            switch (value.get(OPERATION_POSITION)) {
                case "added" -> setResultString("  + " + key + ": " + value.get(VALUE_POSITION) + "\n");
                case "deleted" -> setResultString("  - " + key + ": " + value.get(VALUE_POSITION) + "\n");
                case "unchanged" -> setResultString("    " + key + ": " + value.get(VALUE_POSITION) + "\n");
                default -> setResultString("  - " + key + ": " + value.get(VALUE_POSITION)
                        + "\n  + " + key + ": " + value.get(CHANGED_VALUE_POSITION) + "\n");
            }
        });
        setResultString("}");
        return resultString.toString();
    }

    private String getPlainRepresentation(LinkedHashMap<String, List<String>> lines) {
        return "";
    }

    private String getJSONRepresentation(LinkedHashMap<String, List<String>> lines) {
        return "";
    }
}
