package hexlet.code;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DiffMaker {
    public static Map<String, List<Object>> generateDiffEntity(Map<String, Object> firstEntity,
                                                               Map<String, Object> secondEntity) {
        Map<String, List<Object>> diff = new LinkedHashMap<>();
        Set<String> keys = new TreeSet<>(firstEntity.keySet());
        keys.addAll(secondEntity.keySet());
        for (String key : keys) {
            if (!firstEntity.containsKey(key)) {
                diff.put(key, Arrays.asList("added", secondEntity.get(key)));
            } else if (!secondEntity.containsKey(key)) {
                diff.put(key, Arrays.asList("deleted", firstEntity.get(key)));
            } else if (firstEntity.get(key) == null && secondEntity.get(key) != null
                    || !firstEntity.get(key).equals(secondEntity.get(key))) {
                diff.put(key, Arrays.asList("changed", firstEntity.get(key), secondEntity.get(key)));
            } else {
                diff.put(key, Arrays.asList("unchanged", firstEntity.get(key)));
            }
        }
        return diff;
    }
}
