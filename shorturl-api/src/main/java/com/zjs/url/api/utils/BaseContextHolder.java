package com.zjs.url.api.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zjs
 */
public class BaseContextHolder {
    private BaseContextHolder() {
        throw new IllegalStateException("Utility class");
    }
    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String key, String value) {
        Map<String, String> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>();
            THREAD_LOCAL.set(map);
        }
        map.put(key, value);
    }
    public static String get(String key){
        Map<String, String> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map.get(key);
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
