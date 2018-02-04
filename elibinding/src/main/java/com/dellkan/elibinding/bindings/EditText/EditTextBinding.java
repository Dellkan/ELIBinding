package com.dellkan.elibinding.bindings.EditText;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.dellkan.elibinding.ViewContext;
import com.dellkan.elibinding.binders.ELISingleBinding;
import com.dellkan.elibinding.binders.ViewToModelBinding;
import com.dellkan.elibinding.modelparsers.ModelConcreteAttribute;

public class EditTextBinding extends ELISingleBinding<EditText, CharSequence> implements ViewToModelBinding<EditText, CharSequence> {
    public EditTextBinding() {
        super(EditText.class, "text");
    }

    @Override
    public void applyToView(ViewContext<EditText> viewContext, CharSequence value) {
        // Do nothing. This is covered by TextViewBinding
    }

    @Override
    public void applyToModel(ViewContext<EditText> viewContext, final ModelConcreteAttribute<CharSequence> attribute) {
        viewContext.getView().addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence value, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence value, int start, int before, int count) {
                if (before != count) {
                    attribute.setValue(value.toString());
                }
            }
            @Override public void afterTextChanged(Editable value) {}
        });
    }
}
