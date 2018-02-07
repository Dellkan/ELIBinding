package com.dellkan.elibinding.bindings.CompoundButton;

import android.widget.CompoundButton;

import com.dellkan.elibinding.BindingContext;
import com.dellkan.elibinding.binders.ELIMultiBinding;
import com.dellkan.enhanced_layout_inflater.ViewAttributes;

import java.util.Arrays;

public class CompoundButtonBinding extends ELIMultiBinding<CompoundButton> {
    public CompoundButtonBinding() {
        super(CompoundButton.class, "checked", "onChecked");
    }

    @Override
    public void setupView(final BindingContext<CompoundButton> bindingContext) {
        if (shouldTrigger(bindingContext)) {
            applyToView(bindingContext);
            final ViewAttributes attributes = bindingContext.getViewAttributes();
            final boolean checked = attributes.contains(getNamespace(), "checked")
                    && bindingContext.isTwoWayBinding(getNamespace(), "checked");
            final boolean onChecked = attributes.contains(getNamespace(), "onChecked");
            if (onChecked || checked) {
                bindingContext.getView().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // Set up event handler
                        if (onChecked) {
                            bindingContext.getModelAttribute(getNamespace(), "onChecked").invoke();
                        }

                        // Set up change handler
                        if (checked && bindingContext.isTwoWayBinding(getNamespace(), "checked")) {
                            bindingContext.getModelAttribute(getNamespace(), "checked").setValue(isChecked);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void applyToView(BindingContext<CompoundButton> bindingContext, String... changedAttributes) {
        if (changedAttributes.length == 0 || Arrays.asList(changedAttributes).contains("checked")) {
            boolean modelValue = (Boolean) bindingContext.getModelAttribute(getNamespace(), "checked").getValue();
            if (bindingContext.getView().isChecked() != modelValue) {
                bindingContext.getView().setChecked(modelValue);
            }
        }
    }
}
