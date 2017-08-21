package com.dellkan.elibinding.util;

import com.dellkan.elibinding.PresentationModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ModelParser {
    private ModelParser() {

    }

    public static List<Field> getModelFields(Class<? extends PresentationModel> clz) {
        return Arrays.asList(clz.getFields());
    }

    public static List<Method> getModelMethods(Class<? extends PresentationModel> clz) {
        return Arrays.asList(clz.getMethods());
    }
}
