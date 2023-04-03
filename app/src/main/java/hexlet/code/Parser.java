package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new YAMLMapper();

    static Map<String, Object> parse(String file, String type) throws Exception {
        var mapper = type.equalsIgnoreCase("yml") ? YAML_MAPPER : JSON_MAPPER;
        return mapper.readValue(file, new TypeReference<>() {
        });
    }
}
