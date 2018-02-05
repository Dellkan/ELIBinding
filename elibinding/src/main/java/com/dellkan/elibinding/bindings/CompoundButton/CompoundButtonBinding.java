package com.dellkan.elibinding.bindings.CompoundButton;

import android.widget.CompoundButton;

import com.dellkan.elibinding.ViewContext;
import com.dellkan.elibinding.binders.ELIMultiBinding;
import com.dellkan.enhanced_layout_inflater.ViewAttributes;

import java.util.Arrays;

public class CompoundButtonBinding extends ELIMultiBinding<CompoundButton> {
    public CompoundButtonBinding() {
        super(CompoundButton.class, "checked", "onChecked");
    }

    @Override
    public void setupView(final ViewContext<CompoundButton> viewContext) {
        if (shouldTrigger(viewContext)) {
            applyToView(viewContext);
            final ViewAttributes attributes = viewContext.getViewAttributes();
            final boolean checked = attributes.contains(getNamespace(), "checked")
                    && viewContext.isTwoWayBinding(getNamespace(), "checked");
            final boolean onChecked = attributes.contains(getNamespace(), "onChecked");
            if (onChecked || checked) {
                viewContext.getView().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // Set up event handler
                        if (onChecked) {
                            viewContext.getModelAttribute(getNamespace(), "onChecked").invoke();
                        }

                        // Set up change handler
                        if (checked && viewContext.isTwoWayBinding(getNamespace(), "checked")) {
                            viewContext.getModelAttribute(getNamespace(), "checked").setValue(isChecked);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void applyToView(ViewContext<CompoundButton> viewContext, String... changedAttributes) {
        if (changedAttributes.length == 0 || Arrays.asList(changedAttributes).contains("checked")) {
            boolean modelValue = (Boolean) viewContext.getModelAttribute(getNamespace(), "checked").getValue();
            if (viewContext.getView().isChecked() != modelValue) {
                viewContext.getView().setChecked(modelValue);
            }
        }
    }
}
