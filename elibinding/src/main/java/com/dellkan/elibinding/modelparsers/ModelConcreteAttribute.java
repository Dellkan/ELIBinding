package com.dellkan.elibinding.modelparsers;

import com.dellkan.elibinding.PresentationModel;

import java.lang.reflect.InvocationTargetException;

public class ModelConcreteAttribute<ValueType> {
    ModelAttribute attribute;
    PresentationModel model;

    public ModelConcreteAttribute(ModelAttribute attribute, PresentationModel model) {
        this.attribute = attribute;
        this.model = model;
    }

    public ValueType getValue() {
        try {
            return (ValueType) attribute.getValue(model);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(ValueType value) {
        try {
            this.attribute.setValue(model, value);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
