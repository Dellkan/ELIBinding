package com.dellkan.elibinding.bindings.View;

import android.view.View;

import com.dellkan.elibinding.binders.ELIEventBinding;
import com.dellkan.elibinding.BindingContext;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;

public class OnClickEventBinding extends ELIEventBinding<View> {
    public OnClickEventBinding() {
        super(View.class, "onClick");
    }

    @Override
    public void setupView(final BindingContext<View> bindingContext, final ModelLinkedValueParser.LinkedMember value) {
        bindingContext.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value.invoke();
            }
        });
    }
}
