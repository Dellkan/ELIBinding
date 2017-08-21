package com.dellkan.elibinding.modelparsers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ModelCallback extends ModelMember {
    private Method method;
    private List<Class<?>> parameters;

    public ModelCallback(Method method) {
        this.method = method;
        this.parameters = Arrays.asList(method.getParameterTypes());
    }

    public void invoke(Object model, Object... params) throws InvocationTargetException, IllegalAccessException {
        method.invoke(model, params);
    }
}
