package com.dellkan.elibinding.binders;

import android.view.View;

import com.dellkan.elibinding.PresentationModel;
import com.dellkan.elibinding.BindingContext;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;

/**
 * Used for event bindings, which is essentially when a method on
 * your {@link PresentationModel} should be called when something happens on
 * the view. For instance, when you'd like {@link View.OnClickListener#onClick(View)} to call
 * {@link PresentationModel#myButtonOnClick()}
 * <br /><br />
 * For now, this is set up to only use {@link ELISingleBinding}, meaning you can only bind one event
 * at a time. Expect this to change in the future.
 * @param <ViewType>
 */
public abstract class ELIEventBinding<ViewType extends View> extends ELISingleBinding<ViewType, ModelLinkedValueParser.LinkedMember> {
    public ELIEventBinding(Class<ViewType> clz, String attributeName) {
        super(clz, attributeName);
    }

    @Override
    public boolean listenForModelChanges() {
        return false;
    }

    /**
     * This is used to bind to your view appropriately.
     * @param bindingContext
     * @param value
     */
    @Override
    public abstract void setupView(BindingContext<ViewType> bindingContext, ModelLinkedValueParser.LinkedMember value);

    /**
     * applyToView isn't interesting in this context.
     * @param bindingContext
     * @param value
     */
    @Override
    public final void applyToView(BindingContext<ViewType> bindingContext, ModelLinkedValueParser.LinkedMember value) {

    }
}
