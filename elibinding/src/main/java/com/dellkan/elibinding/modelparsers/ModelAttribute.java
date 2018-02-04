package com.dellkan.elibinding.modelparsers;

import android.support.annotation.NonNull;

import com.dellkan.elibinding.PresentationModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ModelAttribute extends ModelMember {
    private Class<?> clz;
    private Field rawField;
    private Method getter;
    private Map<Class, Method> setters = new HashMap<>();
    private String name;

    public ModelAttribute(Class<?> clz, String name) {
        this.clz = clz;
        this.name = name;
    }

    public ModelAttribute(Class<?> clz, Field attribute) {
        this.clz = clz;
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
            // If we're still here, then we haven't found an appropriate getter yet.
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

            if (model instanceof PresentationModel) {
                ((PresentationModel) model).refresh(name);
            }

            return;
        }

        // If we're still here, then we haven't found an appropriate setter yet.
        // Abandon all hope ye who enters
        throw new NoSuchFieldError("There is no setter for " + name);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModelAttribute)) {
            return false;
        }
        ModelAttribute otherAttribute = (ModelAttribute) other;
        return this.clz.equals(otherAttribute.clz) && this.name.equals(otherAttribute.name);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{clz, rawField, name});
    }
}
