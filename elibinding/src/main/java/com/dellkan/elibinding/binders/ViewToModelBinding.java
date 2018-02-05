package com.dellkan.elibinding.binders;

import android.view.View;

import com.dellkan.elibinding.ViewContext;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;

public interface ViewToModelBinding<ViewType extends View, ValueType> {
    public void applyToModel(ViewContext<ViewType> viewContext, ModelLinkedValueParser.LinkedMember<ValueType> attribute);
}
