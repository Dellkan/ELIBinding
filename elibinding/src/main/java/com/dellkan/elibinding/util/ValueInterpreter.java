package com.dellkan.elibinding.util;

import com.dellkan.elibinding.PresentationModel;
import com.dellkan.elibinding.layoutparsers.ReferenceParser;
import com.dellkan.elibinding.layoutparsers.ValueParser;
import com.dellkan.elibinding.layoutparsers.EnumParser;
import com.dellkan.elibinding.layoutparsers.ModelEventParser;
import com.dellkan.elibinding.layoutparsers.ModelValueParser;
import com.dellkan.elibinding.layoutparsers.RawStringParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * What this is: An authority on our {@link ValueParser}'s that collects them, and
 * whenever we need a value from the model, this will interrogate all the parsers, find a
 * suitable match, and use that parser to convert the value to something usable
 */
public class ValueInterpreter {
    private static List<ValueParser> parsers = Arrays.asList(
            new EnumParser(),
            new ModelValueParser(),
            new ModelEventParser(),
            new ReferenceParser(),
            new RawStringParser()
    );

    public static List<ValueParser> getParsers() {
        return new ArrayList<>(parsers);
    }

    public static ValueParser getParser(Class<? extends ValueParser> parserClass) {
        for (ValueParser parser : parsers) {
            if (parser.getClass().equals(parserClass)) {
                return parser;
            }
        }
        return null;
    }

    public static void addParsers(ValueParser parser) {
        parsers.add(parser);
    }

    public static void removeParser(ValueParser parser) {
        parsers.remove(parser);
    }

    public static ValueParser findSuitableParser(PresentationModel model, String attributeValue) {
        for (ValueParser parser : getParsers()) {
            if (parser.accept(model, attributeValue)) {
                try {
                    return parser;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Object getValue(PresentationModel model, String attributeValue) {
        ValueParser parser = findSuitableParser(model, attributeValue);
        if (parser != null) {
            try {
                return parser.getValue(model, attributeValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
