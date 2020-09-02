package Helpers;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapHelper {

    /**
     * Method to shorten map.stream().filter().collect() to a single line
     *
     * @param map
     * Map to work with
     * @param predicate
     * Filtering predicate
     * @param <K>
    * Any object
     * @param <V>
    * Any object
     * @return
     * Filtered map
     */
    public static<K, V> Map<K, V> filter(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
        return map.entrySet()
                .stream()
                .filter(predicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Method to shorten map.stream().min().get() to a single line
     *
     * @param map
     * Map to work with
     * @param comparator
     * Sorting function
     * @param <K>
     * Any object
     * @param <V>
     * Any object
     * @return
     * Min possible item from map according to comparator, if map is empty - null
     */
    public static<K, V> Map.Entry<K, V> min(Map<K, V> map, Comparator<? super Map.Entry<K, V>> comparator) {
        return map.entrySet()
                .stream()
                .min(comparator).orElse(null);
    }
}


