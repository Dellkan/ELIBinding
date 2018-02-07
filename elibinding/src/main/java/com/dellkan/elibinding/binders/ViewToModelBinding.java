package com.dellkan.elibinding.binders;

import android.view.View;

import com.dellkan.elibinding.BindingContext;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;

public interface ViewToModelBinding<ViewType extends View, ValueType> {
    public void applyToModel(BindingContext<ViewType> bindingContext, ModelLinkedValueParser.LinkedMember<ValueType> attribute);
}
