package com.dellkan.elibinding.layoutparsers;

import com.dellkan.elibinding.PresentationModel;
import com.dellkan.elibinding.modelparsers.ModelCallback;

import java.lang.reflect.Method;

/**
 * Will more than likely change to "EventParser" very soon
 */
public class ModelEventParser extends ModelLinkedValueParser implements ValueParser {
	@Override
	public boolean accept(PresentationModel model, String value) {
		return value.endsWith("()");
	}

	@Override
	public String stripFormattingSymbols(String value) {
		value = value.endsWith("()") ? value.substring(0, value.length() - 2) : value;
		return value;
	}

	@Override
	public ModelLinkedValueParser.LinkedMember getValue(PresentationModel model, String value) throws ReflectiveOperationException {
		return getLinkedValue(model, stripFormattingSymbols(value));
	}
}
