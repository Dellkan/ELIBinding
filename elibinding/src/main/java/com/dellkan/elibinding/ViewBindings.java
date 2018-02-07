package com.dellkan.elibinding;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewBindings<ViewType extends View> {
    private List<BindingContext<ViewType>> bindings = new ArrayList<>();

    public void attach(ViewType view) {
        view.setTag(R.id.bindings, this);
    }

    public void deattach(ViewType view) {
        // No-op. Probably not needed
    }

    @SuppressWarnings("unchecked")
    public void addBinding(BindingContext<ViewType> bindingContext) {
        this.bindings.add(bindingContext);
    }

    public static <ViewType extends View> ViewBindings<ViewType> getOrCreate(ViewType view) {
        @SuppressWarnings("unchecked")
        ViewBindings<ViewType> bindings = (ViewBindings<ViewType>) view.getTag(R.id.bindings);

        if (bindings == null) {
            bindings = new ViewBindings<>();
            bindings.attach(view);
        }

        return bindings;
    }

    public static <ViewType extends View> ViewBindings<ViewType> addBinding(ViewType view, BindingContext<ViewType> bindingContext) {
        ViewBindings<ViewType> bindings = getOrCreate(view);

        bindings.addBinding(bindingContext);

        return bindings;
    }

    public static <ViewType extends View> ViewBindings<ViewType> rebindWithNewModel(ViewType view, PresentationModel model) {
        ViewBindings<ViewType> bindings = getOrCreate(view);

        for (BindingContext<ViewType> binding : bindings.bindings) {
            binding.rebindWithNewModel(model);
        }

        // Do the same for all the children of the target view
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                rebindWithNewModel(viewGroup.getChildAt(i), model);
            }
        }

        return bindings;
    }
}
