package com.dellkan.elibinding.bindings.TextView;

import android.widget.TextView;

import com.dellkan.elibinding.binders.ELISingleBinding;
import com.dellkan.elibinding.BindingContext;

public class TextViewBinding extends ELISingleBinding<TextView, String> {
    public TextViewBinding() {
        super(TextView.class, "text");
    }

    @Override
    public void applyToView(BindingContext<TextView> bindingContext, String value) {
        if (!bindingContext.getView().getText().toString().equals(value)) {
            bindingContext.getView().setText(value);
        }
    }
}
