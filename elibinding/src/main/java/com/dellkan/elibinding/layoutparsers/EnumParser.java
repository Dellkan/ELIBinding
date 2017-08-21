package com.dellkan.elibinding.layoutparsers;

import com.dellkan.elibinding.PresentationModel;

public class EnumParser implements ValueParser {
	@Override
	public boolean accept(PresentationModel model, String value) {
		return false;
	}

	@Override
	public java.lang.Object getValue(PresentationModel model, String value) {
		return null;
	}
}
