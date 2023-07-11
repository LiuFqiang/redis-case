package org.pigliu.rediscase;

import cn.hutool.core.lang.hash.Hash;

import java.time.LocalDateTime;
import java.util.*;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/6/30
 */
public class CollectionsTest {

    private static LocalDateTime localDateTime = LocalDateTime.now();

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("1", 2);
        map.put("2", 2);
        HashSet<Object> set = new HashSet<>();
        set.add("1");
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("1", "1");
        Objects.hashCode(linkedHashMap);
    }
}
