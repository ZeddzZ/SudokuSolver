package Helpers;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapHelper {

    public static<K, V> Map<K, V> filter(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
        return map.entrySet()
                .stream()
                .filter(predicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static<K, V> Map.Entry<K, V> min(Map<K, V> map, Comparator<? super Map.Entry<K, V>> comparator) {
        return map.entrySet()
                .stream()
                .min(comparator).get();
    }
}


