package com.dellkan.elibinding.layoutparsers;

import com.dellkan.elibinding.PresentationModel;

public class ReferenceParser implements ValueParser {
    @Override
    public boolean accept(PresentationModel model, String value) {
        return value.startsWith("@") &&  value.substring(1).matches("\\d+");
    }

    @Override
    public Object getValue(PresentationModel model, String value) throws Exception {
        return Integer.parseInt(value.substring(1));
    }
}
