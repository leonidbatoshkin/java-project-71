package hexlet.code.formatters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static hexlet.code.Utils.processComplexType;

public class PlainFormatter {
    private static final int OPERATION_POSITION = 0;
    private static final int VALUE_POSITION = 1;
    private static final int CHANGED_VALUE_POSITION = 2;
    private final StringBuilder resultString;
    private final LinkedHashMap<String, List<Object>> lines;

    public PlainFormatter(LinkedHashMap<String, List<Object>> lines) {
        this.resultString = new StringBuilder();
        this.lines = lines;
    }

    public void setResultString(String line) {
        resultString.append(line);
    }

    public String getRepresentation() {
        Set<Map.Entry<String, List<Object>>> entries = lines.entrySet();
        entries.forEach(entry -> {
            var key = entry.getKey();
            var value = entry.getValue();
            if (value.get(OPERATION_POSITION).equals("added")) {
                setResultString("Property '" + key + "' was added with value: ");
                setResultString(processComplexType(value.get(VALUE_POSITION)));
            } else if (value.get(OPERATION_POSITION).equals("deleted")) {
                setResultString("Property '" + key + "' was removed");
            } else if (value.get(OPERATION_POSITION).equals("unchanged")) {
                return;
            } else {
                setResultString("Property '" + key + "' was updated. From "
                        + processComplexType(value.get(VALUE_POSITION)) + " to "
                        + processComplexType(value.get(CHANGED_VALUE_POSITION)));
            }
            setResultString("\n");
        });
        return resultString.deleteCharAt(resultString.length() - 1).toString();
    }
}
