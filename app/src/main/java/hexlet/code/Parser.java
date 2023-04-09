package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {

    static Map<String, Object> parse(String file, String type) throws Exception {
        return switch (type) {
            case "json" -> new ObjectMapper().readValue(file, new TypeReference<>() {
            });
            case "yml" -> new YAMLMapper().readValue(file, new TypeReference<>() {
            });
            default -> throw new UnsupportedOperationException(
                    "Only JSON or YAML file formats are available now");
        };
    }
}
