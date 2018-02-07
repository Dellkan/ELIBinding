package com.dellkan.elibinding.binders;

import android.view.View;

import com.dellkan.elibinding.BindingContext;
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
    public boolean shouldTrigger(BindingContext bindingContext, String... changedAttributes) {
        // If the view isn't bound towards us at all, then forget about it
        if (!bindingContext.getViewAttributes().contains(getNamespace(), attributeName, null)) { return false; }
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
    public final void setupView(BindingContext<ViewType> bindingContext) {
        if (shouldTrigger(bindingContext)) {
            //noinspection unchecked
            setupView(bindingContext, (ValueType) ValueInterpreter.getValue(
                    bindingContext.getModel(),
                    bindingContext.getViewAttributes().getValue(getNamespace(), attributeName).getRawValue()
            ));
        }
    }

    public void setupView(BindingContext<ViewType> bindingContext, ValueType value) {
        //noinspection unchecked
        this.applyToView(bindingContext, value);

        // Also apply ViewToModelBinding if appropriate
        String viewAttributeValue = bindingContext.getViewAttributes().getValue(getNamespace(), attributeName).getRawValue();
        if (this instanceof ViewToModelBinding && bindingContext.isTwoWayBinding(getNamespace(), attributeName)) {
            //noinspection unchecked
            ((ViewToModelBinding) this).applyToModel(
                    bindingContext,
                    bindingContext.getModelAttribute(getNamespace(), attributeName)
            );
        }
    }

    @Override
    public final void applyToView(BindingContext<ViewType> bindingContext, String... changedAttributes) {
        //noinspection unchecked
        this.applyToView(
                bindingContext,
            (ValueType) ValueInterpreter.getValue(
                bindingContext.getModel(),
                bindingContext.getViewAttributes().getValue(getNamespace(), attributeName).getRawValue()
            )
        );
    }

    public abstract void applyToView(BindingContext<ViewType> bindingContext, ValueType value);
}
