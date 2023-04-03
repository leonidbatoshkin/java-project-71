package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {

    static ObjectMapper jsonMapper = new ObjectMapper();
    static ObjectMapper yamlMapper = new YAMLMapper();

    static Map<String, String> parse(String file, String type) throws Exception {
        var mapper = type.equalsIgnoreCase("yml") ? yamlMapper : jsonMapper;
        return mapper.readValue(file, new TypeReference<>() {
        });
    }
}
