package com.dellkan.elibinding.bindings.AdapterView;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.dellkan.elibinding.BindingContext;
import com.dellkan.elibinding.binders.ELIMultiBinding;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;
import com.dellkan.elibinding.util.ValueInterpreter;

import java.util.Arrays;

public class AdapterViewBinding extends ELIMultiBinding<AdapterView> {
    public AdapterViewBinding() {
        super(AdapterView.class, "listItems", "listLayout");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setupView(BindingContext<AdapterView> bindingContext) {
        if (shouldTrigger(bindingContext)) {
            String listLayoutRawValue = bindingContext.getViewAttributes().getValue(getNamespace(), "listLayout").getRawValue();
            ModelLinkedValueParser.LinkedMember listItems = bindingContext.getModelAttribute(
                    getNamespace(),
                    "listItems"
            );
            try {
                bindingContext.getView().setAdapter(new ELIDataAdapter(
                        bindingContext,
                        // Reference to model attribute holding the item collection
                        listItems,
                        // Reference to layoutRes attribute
                        (Integer) ValueInterpreter.findSuitableParser(
                                bindingContext.getModel(),
                                listLayoutRawValue
                        ).getValue(
                                bindingContext.getModel(),
                                listLayoutRawValue
                        )
                ));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void applyToView(BindingContext<AdapterView> bindingContext, String... changedAttributes) {
        if (changedAttributes.length == 0 || Arrays.asList(changedAttributes).contains(getNamespace() + ":listItems")) {
            Adapter adapter = bindingContext.getView().getAdapter();
            if (adapter instanceof BaseAdapter) {
                ((BaseAdapter) adapter).notifyDataSetChanged();
            }
        }
    }
}
