package com.dellkan.elibinding.binders;

import android.widget.EditText;

import com.dellkan.elibinding.BindingContext;

public interface ModelToViewBinding<ValueType> {
    public void applyToView(BindingContext<EditText> bindingContext, ValueType value);
}
