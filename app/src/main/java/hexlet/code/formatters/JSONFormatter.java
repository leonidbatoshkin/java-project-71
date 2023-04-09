package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public final class JSONFormatter {

    public static String getRepresentation(Map<String, List<Object>> lines) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(lines);
    }
}
