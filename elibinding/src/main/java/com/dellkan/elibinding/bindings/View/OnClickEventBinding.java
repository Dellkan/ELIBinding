package com.dellkan.elibinding.bindings.View;

import android.view.View;

import com.dellkan.elibinding.binders.ELIEventBinding;
import com.dellkan.elibinding.ViewContext;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;
import com.dellkan.elibinding.modelparsers.ModelCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OnClickEventBinding extends ELIEventBinding<View> {
    public OnClickEventBinding() {
        super(View.class, "onClick");
    }

    @Override
    public void setupView(final ViewContext<View> viewContext, final ModelLinkedValueParser.LinkedMember value) {
        viewContext.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value.invoke();
            }
        });
    }
}
