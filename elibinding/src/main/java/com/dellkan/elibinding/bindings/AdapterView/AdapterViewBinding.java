package com.dellkan.elibinding.bindings.AdapterView;

import android.widget.AdapterView;

import com.dellkan.elibinding.ViewContext;
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
    public void setupView(ViewContext<AdapterView> viewContext) {
        if (shouldTrigger(viewContext)) {
            String listLayoutRawValue = viewContext.getViewAttributes().getValue(getNamespace(), "listLayout").getRawValue();
            ModelLinkedValueParser.LinkedMember listItems = viewContext.getModelAttribute(
                    getNamespace(),
                    "listItems"
            );
            try {
                viewContext.getView().setAdapter(new ELIDataAdapter(
                        viewContext,
                        // Reference to model attribute holding the item collection
                        listItems,
                        // Reference to layoutRes attribute
                        (Integer) ValueInterpreter.findSuitableParser(
                                viewContext.getModel(),
                                listLayoutRawValue
                        ).getValue(
                                viewContext.getModel(),
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
    public void applyToView(ViewContext<AdapterView> viewContext, String... changedAttributes) {
        if (Arrays.asList(changedAttributes).contains("listItems")) {

        }
    }
}
