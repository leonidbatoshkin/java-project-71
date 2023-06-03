package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public final class PlainFormatter {
    private static final int OPERATION_POSITION = 0;
    private static final int VALUE_POSITION = 1;
    private static final int CHANGED_VALUE_POSITION = 2;

    public static String getRepresentation(Map<String, List<Object>> lines) {
        StringBuilder resultString = new StringBuilder();
        lines.forEach((key, value) -> {
            switch (String.valueOf(value.get(OPERATION_POSITION))) {
                case "added" -> resultString.append("Property '").append(key).append("' was added with value: ")
                        .append(processComplexType(value.get(VALUE_POSITION))).append("\n");
                case "deleted" -> resultString.append("Property '").append(key).append("' was removed").append("\n");
                case "unchanged" -> {
                    return;
                }
                case "changed" -> resultString.append("Property '").append(key).append("' was updated. From ")
                        .append(processComplexType(value.get(VALUE_POSITION))).append(" to ")
                        .append(processComplexType(value.get(CHANGED_VALUE_POSITION))).append("\n");
                default -> throw new UnsupportedOperationException("Couldn't make representation for this data");
            }
        });
        return resultString.deleteCharAt(resultString.length() - 1).toString();
    }

    public static String processComplexType(Object obj) {
        if (obj instanceof String) {
            return "'" + obj + "'";
        } else if (obj instanceof Map<?, ?> || obj instanceof List<?>) {
            return "[complex value]";
        }
        return String.valueOf(obj);
    }
}
