package com.dellkan.elibinding.modelparsers;

import com.dellkan.elibinding.PresentationModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Think of this as a scan of your model - containing a lookup of all its attributes and methods.
 * This is done more or less just to avoid repeated reflection, and to make lookups easier and less
 * error prone. The idea is that every unique model should be scanned just once.
 */
public class ModelScanner {
    // You're not supposed to instantiate this class..
    private ModelScanner() {}

    private static Map<String, ModelScan> scannedModels = new HashMap<>();

    public static ModelScan getScan(Class<?> model) {
        String name = model.getCanonicalName();
        if (!scannedModels.containsKey(name)) {
            scannedModels.put(name, new ModelScan(model));
        }
        return scannedModels.get(name);
    }

    private static List<String> attributePrefixes = Arrays.asList("get", "set", "is");
    private static boolean isAttribute(String rawAttributeName) {
        for (String prefix : attributePrefixes) {
            if (rawAttributeName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static String getAttributeName(String rawAttributeName) {
        for (String prefix : attributePrefixes) {
            if (rawAttributeName.startsWith(prefix)) {
                return rawAttributeName.substring(prefix.length());
            }
        }
        return rawAttributeName;
    }

    public static class ModelScan {
        private Map<String, ModelAttribute> attributeAccessors = new HashMap<>();
        private Map<String, ModelCallback> callbacks = new HashMap<>();

        public ModelScan(Class<?> model) {
            // Run through the fields (attributes)
            for (Field field : model.getFields()) {
                // Skip attributes belonging to Object
                if (field.getDeclaringClass().equals(Object.class)) { continue; }
                attributeAccessors.put(field.getName(), new ModelAttribute(field));
            }

            // Run through methods (attributes accessors & callbacks)
            for (Method method : model.getMethods()) {
                // Skip methods from Object
                if (method.getDeclaringClass().equals(Object.class)) { continue; }
                String rawAttributeName = method.getName();
                // This is an attribute
                if (isAttribute(rawAttributeName)) {
                    String attributeName = getAttributeName(rawAttributeName);
                    ModelAttribute attribute = attributeAccessors.get(attributeName);
                    if (attribute == null) {
                        attribute = new ModelAttribute(attributeName);
                        attributeAccessors.put(attributeName, attribute);
                    }

                    attribute.addAccessor(method);
                } else { // It's not an attribute - assume its a callback
                    callbacks.put(rawAttributeName, new ModelCallback(method));
                }
            }

            for (ModelAttribute attribute : getAttributes()) {
                if (PresentationModel.class.isAssignableFrom(attribute.getType())) {
                    getScan(attribute.getType());
                }
            }
        }

        public Collection<ModelAttribute> getAttributes() {
            return attributeAccessors.values();
        }

        public boolean hasAttribute(String name) {
            return attributeAccessors.containsKey(name);
        }

        public ModelAttribute getAttribute(String name) {
            return attributeAccessors.get(name);
        }

        public boolean hasCallback(String name) {
            return callbacks.containsKey(name);
        }

        public ModelCallback getCallback(String name) {
            return callbacks.get(name);
        }
    }
}
