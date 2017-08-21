package com.dellkan.elibinding.modelparsers;

import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ModelAttribute extends ModelMember {
    private Field rawField;
    private Method getter;
    private Map<Class, Method> setters = new HashMap<>();
    private String name;

    public ModelAttribute(String name) {
        this.name = name;
    }

    public ModelAttribute(Field attribute) {
        this.rawField = attribute;
        this.name = attribute.getName();
    }

    public void addAccessor(Method method) {
        boolean isSetter = method.getName().startsWith("set");
        if (isSetter) {
            // There might be multiple setters, converting between values.
            setters.put(method.getParameterTypes()[0], method);
        } else {
            if (getter != null) {
                throw new RuntimeException("Can't have multiple getters for same property");
            }
            getter = method;
        }
    }

    public Class<?> getType() {
        if (getter != null) {
            return getter.getReturnType();
        } else if (rawField != null) {
            return rawField.getType();
        } else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public Object getValue(@NonNull Object model) throws InvocationTargetException, IllegalAccessException {
        if (getter != null) {
            return getter.invoke(model);
        } else if (rawField != null) {
            return rawField.get(model);
        } else {
            // If we're still here, then we haven't found an appropriate setter yet.
            // Abandon all hope ye who enters
            throw new NoSuchFieldError("There is no getter for " + name);
        }
    }

    public void setValue(@NonNull Object model, Object value) throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<Class, Method> setter : setters.entrySet()) {
            if (setter.getKey().isAssignableFrom(value.getClass())) {
                setter.getValue().invoke(model, value);
                return; // All done.
            }
        }

        if (rawField != null) {
            rawField.set(model, value);
            return;
        }

        // If we're still here, then we haven't found an appropriate setter yet.
        // Abandon all hope ye who enters
        throw new NoSuchFieldError("There is no setter for " + name);
    }
}
