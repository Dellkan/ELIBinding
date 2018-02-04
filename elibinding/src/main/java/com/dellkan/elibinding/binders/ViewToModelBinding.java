package com.dellkan.elibinding.binders;

import android.view.View;

import com.dellkan.elibinding.ViewContext;
import com.dellkan.elibinding.modelparsers.ModelConcreteAttribute;

public interface ViewToModelBinding<ViewType extends View, ValueType> {
    public void applyToModel(ViewContext<ViewType> viewContext, ModelConcreteAttribute<ValueType> attribute);
}
