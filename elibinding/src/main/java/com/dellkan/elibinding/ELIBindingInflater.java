package com.dellkan.elibinding;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dellkan.elibinding.bindings.AdapterView.AdapterViewBinding;
import com.dellkan.elibinding.bindings.CompoundButton.CompoundButtonBinding;
import com.dellkan.elibinding.bindings.EditText.EditTextBinding;
import com.dellkan.elibinding.bindings.TextView.TextViewBinding;
import com.dellkan.elibinding.bindings.View.OnClickEventBinding;
import com.dellkan.elibinding.modelparsers.ModelScanner;
import com.dellkan.enhanced_layout_inflater.ELI;
import com.dellkan.enhanced_layout_inflater.ELIConfig;
import com.dellkan.enhanced_layout_inflater.ELIContext;

import java.util.MissingFormatArgumentException;

public class ELIBindingInflater {
	private ELI mInflater;
	public ELIBindingInflater(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (layoutInflater instanceof ELI) {
			mInflater = (ELI) layoutInflater;
		} else {
			mInflater = new ELI(layoutInflater, context, false, new ELIConfig.Builder()
					.addHook(new TextViewBinding())
					.addHook(new OnClickEventBinding())
					.addHook(new EditTextBinding())
					.addHook(new AdapterViewBinding())
					.addHook(new CompoundButtonBinding())
					.build()
			);
		}
	}

	/*
	 * Inflater methods copy
	 */
	/**
	 * Inflate a new view hierarchy from the specified xml resource. Throws
	 * {@link InflateException} if there is an error.
	 *
	 * @param resource ID for an XML layout resource to load (e.g.,
	 *        <code>R.layout.main_page</code>)
	 * @param root Optional view to be the parent of the generated hierarchy.
	 * @return The root View of the inflated hierarchy. If root was supplied,
	 *         this is the root View; otherwise it is the root of the inflated
	 *         XML file.
	 */
	public View inflate(@NonNull PresentationModel model, @LayoutRes int resource, @Nullable ViewGroup root) {
		return inflate(model, resource, root, root != null);
	}

	/**
	 * Inflate a new view hierarchy from the specified xml resource. Throws
	 * {@link InflateException} if there is an error.
	 *
	 * @param resource ID for an XML layout resource to load (e.g.,
	 *        <code>R.layout.main_page</code>)
	 * @param root Optional view to be the parent of the generated hierarchy (if
	 *        <em>attachToRoot</em> is true), or else simply an object that
	 *        provides a set of LayoutParams values for root of the returned
	 *        hierarchy (if <em>attachToRoot</em> is false.)
	 * @param attachToRoot Whether the inflated hierarchy should be attached to
	 *        the root parameter? If false, root is only used to create the
	 *        correct subclass of LayoutParams for the root view in the XML.
	 * @return The root View of the inflated hierarchy. If root was supplied and
	 *         attachToRoot is true, this is root; otherwise it is the root of
	 *         the inflated XML file.
	 */
	public View inflate(@NonNull PresentationModel model, @LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
		if (model == null) {
			throw new MissingFormatArgumentException("Model can't be null");
		}

        ModelScanner.getScan(model.getClass());

        return mInflater.inflate(resource, root, attachToRoot, new ELIContext.Builder().addData("ELIBindingModel", model));
	}
}
