package com.dellkan.elibinding.util;

import java.util.HashMap;
import java.util.Map;

public class TypeUtils {
    private static Map<Class<?>, Class<?>> primitiveMap = new HashMap<>();
    static {
        primitiveMap.put(Boolean.TYPE, Boolean.class);
        primitiveMap.put(Byte.TYPE, Byte.class);

        primitiveMap.put(Character.TYPE, Character.class);

        primitiveMap.put(Short.TYPE, Short.class);
        primitiveMap.put(Integer.TYPE, Integer.class);
        primitiveMap.put(Long.TYPE, Long.class);
        primitiveMap.put(Float.TYPE, Float.class);
        primitiveMap.put(Double.TYPE, Double.class);

        primitiveMap.put(Void.TYPE, Void.class);
    }

    public static Class<?> convertPrimitiveClass(Class<?> clz) {
        return primitiveMap.containsKey(clz) ? primitiveMap.get(clz) : clz;
    }
}
