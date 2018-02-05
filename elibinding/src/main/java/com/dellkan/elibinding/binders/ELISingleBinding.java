package com.dellkan.elibinding.binders;

import android.view.View;

import com.dellkan.elibinding.ViewContext;
import com.dellkan.elibinding.util.ValueInterpreter;

public abstract class ELISingleBinding<ViewType extends View, ValueType> extends ELIBinding<ViewType> {
    private String attributeName;
    private String qualifiedAttributeName;
    protected ELISingleBinding(Class<ViewType> clz, String attributeName) {
        super(clz);
        this.attributeName = attributeName;
        this.qualifiedAttributeName = String.format("%s:%s", getNamespace(), attributeName);
    }

    @Override
    public boolean shouldTrigger(ViewContext viewContext, String... changedAttributes) {
        // If the view isn't bound towards us at all, then forget about it
        if (!viewContext.getViewAttributes().contains(getNamespace(), attributeName, null)) { return false; }
        // Empty changedAttributes means refresh all
        if (changedAttributes.length == 0) { return true; }

        // Otherwise, check if ours is affected
        for (String attribute : changedAttributes) {
            if (attribute.equals(qualifiedAttributeName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final void setupView(ViewContext<ViewType> viewContext) {
        if (shouldTrigger(viewContext)) {
            //noinspection unchecked
            setupView(viewContext, (ValueType) ValueInterpreter.getValue(
                    viewContext.getModel(),
                    viewContext.getViewAttributes().getValue(getNamespace(), attributeName).getRawValue()
            ));
        }
    }

    public void setupView(ViewContext<ViewType> viewContext, ValueType value) {
        //noinspection unchecked
        this.applyToView(viewContext, value);

        // Also apply ViewToModelBinding if appropriate
        String viewAttributeValue = viewContext.getViewAttributes().getValue(getNamespace(), attributeName).getRawValue();
        if (this instanceof ViewToModelBinding && viewContext.isTwoWayBinding(getNamespace(), attributeName)) {
            //noinspection unchecked
            ((ViewToModelBinding) this).applyToModel(
                    viewContext,
                    viewContext.getModelAttribute(getNamespace(), attributeName)
            );
        }
    }

    @Override
    public final void applyToView(ViewContext<ViewType> viewContext, String... changedAttributes) {
        //noinspection unchecked
        this.applyToView(
            viewContext,
            (ValueType) ValueInterpreter.getValue(
                viewContext.getModel(),
                viewContext.getViewAttributes().getValue(getNamespace(), attributeName).getRawValue()
            )
        );
    }

    public abstract void applyToView(ViewContext<ViewType> viewContext, ValueType value);
}
