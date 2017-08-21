package com.dellkan.elibinding;

import com.dellkan.elibinding.modelparsers.ModelScanner;
import com.dellkan.elibinding.util.ModelParser;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelChangeHandler {
    private static Map<Object, List<Listener>> listeners = new HashMap<>();
    public static void register(Object model) {
        ModelScanner.getScan(model.getClass());
    }

    public static void refresh(Object model, String... modelFieldName) {
        if (listeners.containsKey(model)) {
            for (Listener listener : listeners.get(model)) {
                listener.onChange(model, modelFieldName);
            }
        }
    }

    public static void registerListener(Object model, Listener listener) {
        if (!listeners.containsKey(model)) {
            listeners.put(model, new ArrayList<Listener>());
        }
        listeners.get(model).add(listener);
    }

    public static interface Listener {
        void onChange(Object model, String... modelFieldName);
    }
}
