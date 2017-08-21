package com.dellkan.elibinding.layoutparsers;

import com.dellkan.elibinding.PresentationModel;

/**
 * Read a single attribute from a single view from your layout xml files, and figures out if it
 * has a value it understands and can use for something useful. Also responsible for converting
 * said value into something comprehensible.
 * <br /><br />
 * Example: xml has a line with bind:text="{test}"<br />
 * {@link ModelValueParser#accept(PresentationModel, String)}
 * thus receives "{test}", which it recognizes as a value it should find in the attached
 * {@link PresentationModel}, and converts to <br />
 * {@link PresentationModel}.test or<br />
 * {@link PresentationModel}.test() or<br />
 * {@link PresentationModel}.getTest() depending on what your
 * presenationModel looks like
 */
public interface ValueParser {
	boolean accept(PresentationModel model, String value);
	Object getValue(PresentationModel model, String value) throws Exception;
}
