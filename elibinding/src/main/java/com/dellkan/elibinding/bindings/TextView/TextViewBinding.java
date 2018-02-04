package com.dellkan.elibinding.bindings.TextView;

import android.widget.TextView;

import com.dellkan.elibinding.binders.ELISingleBinding;
import com.dellkan.elibinding.ViewContext;

public class TextViewBinding extends ELISingleBinding<TextView, String> {
    public TextViewBinding() {
        super(TextView.class, "text");
    }

    @Override
    public void applyToView(ViewContext<TextView> viewContext, String value) {
        if (!viewContext.getView().getText().toString().equals(value)) {
            viewContext.getView().setText(value);
        }
    }
}
