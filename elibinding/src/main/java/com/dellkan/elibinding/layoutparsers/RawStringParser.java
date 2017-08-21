package com.dellkan.elibinding.layoutparsers;

import com.dellkan.elibinding.PresentationModel;

public class RawStringParser implements ValueParser {
    @Override
    public boolean accept(PresentationModel model, String value) {
        return !value.startsWith("{") &&
                !value.endsWith("}") &&
                !value.endsWith("()");
    }

    @Override
    public java.lang.Object getValue(PresentationModel model, String value) throws ReflectiveOperationException {
        return value;
    }
}
