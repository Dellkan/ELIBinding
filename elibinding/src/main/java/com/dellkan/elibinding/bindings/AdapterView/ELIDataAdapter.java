package com.dellkan.elibinding.bindings.AdapterView;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.dellkan.elibinding.ELIBindingInflater;
import com.dellkan.elibinding.PresentationModel;
import com.dellkan.elibinding.BindingContext;
import com.dellkan.elibinding.ViewBindings;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;

import java.util.List;

/**
 * Generic purpose ListView type data-adapter, suitable for generic-purpose databinding-listviews
 */
public class ELIDataAdapter extends BaseAdapter {
    private BindingContext<AdapterView> bindingContext;
    private ModelLinkedValueParser.LinkedMember<List<PresentationModel>> itemSource;
    private @LayoutRes int layoutRes;

    public ELIDataAdapter(BindingContext<AdapterView> bindingContext, ModelLinkedValueParser.LinkedMember<List<PresentationModel>> itemSource, int layoutRes) {
        this.bindingContext = bindingContext;
        this.itemSource = itemSource;
        this.layoutRes = layoutRes;
    }

    @Override
    public int getCount() {
        return itemSource.getValue().size();
    }

    @Override
    public PresentationModel getItem(int position) {
        return itemSource.getValue().get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    /**
     * Get or create the view, while also attaching the correct model
     *
     * FIXME: Currently broken - does not refresh the bound model
     * (which will change as you scroll due to reused views)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        View view = convertView;

        if (view != null) {
            // Refresh the view with the new model somehow
            ViewBindings.rebindWithNewModel(view, getItem(position));
        } else {
            view = new ELIBindingInflater(context).inflate(getItem(position), layoutRes, null);
        }

        return view;
    }
}
