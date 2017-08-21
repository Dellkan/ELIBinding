package com.dellkan.elibinding.layoutparsers;

import com.dellkan.elibinding.PresentationModel;

/**
 * Will more than likely incorporate method getters lookup very soon
 */
public class ModelValueParser extends ModelLinkedValueParser implements ValueParser {
	@Override
	public boolean accept(PresentationModel model, String value) {
		return (value.startsWith("{") || value.startsWith("${")) && value.endsWith("}");
	}

	@Override
	public String stripFormattingSymbols(String value) {
		value = (value.startsWith("{") ? value.substring(1) : value);
		value = (value.endsWith("}") ? value.substring(0, value.length() - 1) : value);
		return value;
	}

	@Override
	public Object getValue(PresentationModel model, String value) throws ReflectiveOperationException {
	    return getLinkedValue(model, stripFormattingSymbols(value)).getValue();
	}
}
