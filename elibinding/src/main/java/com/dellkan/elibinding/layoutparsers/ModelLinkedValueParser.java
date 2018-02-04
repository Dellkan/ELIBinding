package com.dellkan.elibinding.layoutparsers;

import com.dellkan.elibinding.modelparsers.ModelAttribute;
import com.dellkan.elibinding.modelparsers.ModelCallback;
import com.dellkan.elibinding.modelparsers.ModelMember;
import com.dellkan.elibinding.modelparsers.ModelScanner;

import java.lang.reflect.InvocationTargetException;

public abstract class ModelLinkedValueParser implements ValueParser {
    public boolean hasLinks(Object model, String value) {
        return value.contains(".");
    }

    public abstract String stripFormattingSymbols(String value);

    public LinkedMember getLinkedValue(Object model, String value) {
        String[] parts = value.split("\\.", 2);

        ModelScanner.ModelScan scan = ModelScanner.getScan(model.getClass());

        if (hasLinks(model, value)) {
            if (!scan.hasAttribute(parts[0])) {
                throw new RuntimeException(String.format("Linked model not found!: %s.%s", model.getClass().toString(), parts[0]));
            }
            // First get our most immediate link
            try {
                return getLinkedValue(scan.getAttribute(parts[0]).getValue(model), parts[1]);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (scan.hasAttribute(parts[0])) {
                return new LinkedMember(model, scan.getAttribute(parts[0]));
            } else if (scan.hasCallback(parts[0])) {
                return new LinkedMember(model, scan.getCallback(parts[0]));
            } else {
                throw new RuntimeException(String.format("Member not found!: %s.%s", model.getClass().toString(), parts[0]));
            }
        }
    }

    public static class LinkedMember {
        Object model;
        ModelMember member;

        public LinkedMember(Object model, ModelMember field) {
            this.model = model;
            this.member = field;
        }

        public Object getModel() {
            return model;
        }

        public ModelMember getMember() {
            return member;
        }

        public Object getValue() {
            try {
                return ((ModelAttribute)member).getValue(model);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public void invoke(Object... params) {
            try {
                ((ModelCallback)member).invoke(model, params);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
