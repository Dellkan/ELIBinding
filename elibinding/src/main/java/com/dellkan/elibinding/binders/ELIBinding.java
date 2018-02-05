package com.dellkan.elibinding.binders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dellkan.elibinding.PresentationModel;
import com.dellkan.elibinding.ViewContext;
import com.dellkan.enhanced_layout_inflater.ELIContext;
import com.dellkan.enhanced_layout_inflater.ViewAttributes;
import com.dellkan.enhanced_layout_inflater.ViewHook;

public abstract class ELIBinding<ViewType extends View> extends ViewHook<ViewType> {
	public static final String defaultNamespace = "http://dellkan.com/elibinding";

	public ELIBinding(Class<ViewType> clz) {
		super(clz);
	}

	@Override
	public void onViewCreatedRaw(ELIContext eliContext, @Nullable View parent, @NonNull ViewType view, @Nullable AttributeSet attrs) {
		if (!super.shouldTrigger(eliContext, parent, view, attrs)) {
			return;
		}

		PresentationModel model = (PresentationModel) eliContext.getData().get("ELIBindingModel");
		ViewAttributes viewAttributes = new ViewAttributes(attrs);

		ViewContext<ViewType> viewContext = new ViewContext<>(eliContext, parent, view, viewAttributes, model, this);
	}

	public String getNamespace() {
	    return defaultNamespace;
    }

	@Override
	public void onViewCreated(ELIContext eliContext, @Nullable View parent, @NonNull ViewType view, @Nullable AttributeSet attrs) {}

    /**
     * If this binding is listening for model changes, this hook checks if the change is
     * interesting or not
     * @param viewContext Collection of data regarding this binding, model, and view
     * @param changedAttributes The attributes on the model that changed
     * @return true if the change should be applied on this binding
     * @see #listenForModelChanges()
     */
	public boolean shouldTrigger(ViewContext viewContext, String... changedAttributes) {
		return true;
	}

    /**
     * Overwrite this if you don't want your hook to react to model changes
     * @return true to listen for model changes, false to ignore them
     */
	public boolean listenForModelChanges() {
	    return true;
    }

    /**
     * Called once every view creation. Use this to initialize your view
     * @param viewContext
     */
	public void setupView(ViewContext<ViewType> viewContext) {
	    applyToView(viewContext);
    }

    /**
     * Called every time the relevant fields on your model changes.
     * @param viewContext
     * @see #shouldTrigger(ViewContext, String...)
     * @see #listenForModelChanges()
     */
	public abstract void applyToView(ViewContext<ViewType> viewContext, String... changedAttributes);
}
