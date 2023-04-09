package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public final class StylishFormatter {
    private static final int OPERATION_POSITION = 0;
    private static final int VALUE_POSITION = 1;
    private static final int CHANGED_VALUE_POSITION = 2;

    public static String getRepresentation(Map<String, List<Object>> lines) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("{\n");
        lines.forEach((key, value) -> {
            switch (String.valueOf(value.get(OPERATION_POSITION))) {
                case "added" -> resultString.append("  + ").append(key).append(": ")
                        .append(value.get(VALUE_POSITION)).append("\n");
                case "deleted" -> resultString.append("  - ").append(key).append(": ")
                        .append(value.get(VALUE_POSITION)).append("\n");
                case "unchanged" -> resultString.append("    ").append(key).append(": ")
                        .append(value.get(VALUE_POSITION)).append("\n");
                case "changed" -> resultString.append("  - ").append(key).append(": ")
                        .append(value.get(VALUE_POSITION)).append("\n  + ").append(key).append(": ").
                        append(value.get(CHANGED_VALUE_POSITION)).append("\n");
                default -> throw new RuntimeException("Couldn't make representation for this data");
            }
        });
        resultString.append("}");
        return resultString.toString();
    }
}
