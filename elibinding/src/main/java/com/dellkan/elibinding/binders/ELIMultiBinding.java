package com.dellkan.elibinding.binders;

import android.view.View;

import com.dellkan.elibinding.ViewContext;
import com.dellkan.enhanced_layout_inflater.ViewAttribute;

import java.util.ArrayList;
import java.util.List;

public abstract class ELIMultiBinding<ViewType extends View> extends ELIBinding<ViewType> {
    List<ViewAttribute> viewAttributes = new ArrayList<>();
    protected ELIMultiBinding(Class<ViewType> clz, String... attributes) {
        super(clz);
        for (String attribute : attributes) {
            viewAttributes.add(new ViewAttribute(getNamespace(), attribute, null));
        }
    }

    @Override
    public boolean shouldTrigger(ViewContext viewContext, String... changedAttributes) {
        // If the view isn't bound towards us at all, then forget about it
        boolean hasRelevantAttribute = false;
        for (ViewAttribute viewAttribute : viewAttributes) {
            if (viewContext.getViewAttributes().contains(getNamespace(), viewAttribute.getAttributeName(), null)) {
                hasRelevantAttribute = true;
                break;
            }
        }
        if (!hasRelevantAttribute) {
            return false;
        }

        // Empty changedAttributes means refresh all
        if (changedAttributes.length == 0) { return true; }

        // Otherwise, check if ours is affected
        for (String attribute : changedAttributes) {
            if (viewAttributes.contains(new ViewAttribute(attribute))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setupView(ViewContext<ViewType> viewContext) {
        if (shouldTrigger(viewContext)) {
            applyToView(viewContext);
        }
    }
}
