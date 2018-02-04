package com.dellkan.elibinding.binders;

import android.widget.EditText;

import com.dellkan.elibinding.ViewContext;

public interface ModelToViewBinding<ValueType> {
    public void applyToView(ViewContext<EditText> viewContext, ValueType value);
}
