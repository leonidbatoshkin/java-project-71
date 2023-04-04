package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.LinkedHashMap;

public class JSONFormatter {
    private final StringBuilder resultString;
    private final LinkedHashMap<String, List<Object>> lines;

    public JSONFormatter(LinkedHashMap<String, List<Object>> lines) {
        this.resultString = new StringBuilder();
        this.lines = lines;
    }

    public void setResultString(String line) {
        resultString.append(line);
    }

    public String getRepresentation() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        setResultString(objectMapper.writeValueAsString(lines));
        return resultString.toString();
    }
}
