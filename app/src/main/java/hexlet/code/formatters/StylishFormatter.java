package hexlet.code.formatters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StylishFormatter {
    private static final int OPERATION_POSITION = 0;
    private static final int VALUE_POSITION = 1;
    private static final int CHANGED_VALUE_POSITION = 2;
    private final StringBuilder resultString;
    private final LinkedHashMap<String, List<Object>> lines;

    public StylishFormatter(LinkedHashMap<String, List<Object>> lines) {
        this.resultString = new StringBuilder();
        this.lines = lines;
    }

    public void setResultString(String line) {
        this.resultString.append(line);
    }

    public String getRepresentation() {
        setResultString("{\n");
        Set<Map.Entry<String, List<Object>>> entries = lines.entrySet();
        entries.forEach(entry -> {
            var key = entry.getKey();
            var value = entry.getValue();
            if (value.get(OPERATION_POSITION).equals("added")) {
                setResultString("  + " + key + ": " + value.get(VALUE_POSITION) + "\n");
            } else if (value.get(OPERATION_POSITION).equals("deleted")) {
                setResultString("  - " + key + ": " + value.get(VALUE_POSITION) + "\n");
            } else if (value.get(OPERATION_POSITION).equals("unchanged")) {
                setResultString("    " + key + ": " + value.get(VALUE_POSITION) + "\n");
            } else {
                setResultString("  - " + key + ": " + value.get(VALUE_POSITION)
                        + "\n  + " + key + ": " + value.get(CHANGED_VALUE_POSITION) + "\n");
            }
        });
        setResultString("}");
        return resultString.toString();
    }
}
