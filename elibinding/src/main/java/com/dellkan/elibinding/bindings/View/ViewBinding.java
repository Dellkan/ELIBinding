package com.dellkan.elibinding.bindings.View;

import android.view.View;

import com.dellkan.elibinding.binders.ELIEventBinding;
import com.dellkan.elibinding.ViewContext;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;
import com.dellkan.elibinding.modelparsers.ModelCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewBinding extends ELIEventBinding<View> {
    public ViewBinding() {
        super(View.class, "onClick");
    }

    @Override
    public void setupView(final ViewContext<View> viewContext, final ModelLinkedValueParser.LinkedMember value) {
        viewContext.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    value.invoke();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
