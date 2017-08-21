package com.dellkan.elibinding;

import android.view.View;

import com.dellkan.elibinding.binders.ELIBinding;
import com.dellkan.elibinding.layoutparsers.ModelLinkedValueParser;
import com.dellkan.elibinding.layoutparsers.ModelValueParser;
import com.dellkan.elibinding.layoutparsers.ValueParser;
import com.dellkan.elibinding.modelparsers.ModelAttribute;
import com.dellkan.elibinding.util.ValueInterpreter;
import com.dellkan.enhanced_layout_inflater.ELIContext;
import com.dellkan.enhanced_layout_inflater.ViewAttribute;
import com.dellkan.enhanced_layout_inflater.ViewAttributes;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * All of the parameters to the {@link ELIBinding#applyToView(ViewContext)} were getting a bit of
 * a handful. This class here is nothing more than a glorified shortcut so we can provide the binding
 * with all of the cool data we have without having 20+ parameters
 * @param <ViewType>
 */
public class ViewContext<ViewType extends View> {
    private ELIContext eliContext;
    private View parent;
    private ViewType view;
    private ViewAttributes viewAttributes;
    private PresentationModel model;
    private ELIBinding<ViewType> binding;
    private Map<ViewAttribute, String> attributeMapping = new HashMap<>();

    public ViewContext(ELIContext eliContext, View parent, ViewType view, ViewAttributes viewAttributes, PresentationModel model, ELIBinding<ViewType> binding) {
        this.eliContext = eliContext;
        this.parent = parent;
        this.view = view;
        this.viewAttributes = viewAttributes;
        this.model = model;
        this.binding = binding;

        bindView();
    }

    public ELIContext getEliContext() {
        return eliContext;
    }

    public View getParent() {
        return parent;
    }

    public ViewType getView() {
        return view;
    }

    public ViewAttributes getViewAttributes() {
        return viewAttributes;
    }

    public PresentationModel getModel() {
        return model;
    }

    private void bindView() {
        // We'll need a map of what modelAttribute maps to which viewAttribute
        Set<Object> models = new HashSet<>();
        models.add(model);
        ModelValueParser valueParser = (ModelValueParser) ValueInterpreter.getParser(ModelValueParser.class);
        for (ViewAttribute attribute : viewAttributes.getValues(binding.getNamespace(), null, null)) {
            if (valueParser.accept(model, attribute.getRawValue())) {
                String value = valueParser.stripFormattingSymbols(attribute.getRawValue());
                if (valueParser.hasLinks(model, value)) {
                    try {
                        ModelLinkedValueParser.LinkedMember linkedValue = valueParser.getLinkedValue(model, value);
                        models.add(linkedValue.getModel());
                        attributeMapping.put(
                                attribute,
                                String.format("%s:%s",
                                        linkedValue.getModel().getClass(),
                                        ((ModelAttribute)linkedValue.getMember()).getName()
                                )
                        );
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    attributeMapping.put(attribute, model.getClass() + ":" + value);
                }
            }
        }

        binding.setupView(this);

        if (binding.listenForModelChanges()) {
            // Setup the listener(s)
            for (Object subModel : models) {
                ModelChangeHandler.registerListener(subModel, new ModelChangeHandler.Listener() {
                    @Override
                    public void onChange(Object model, String... refreshedAttributes) {
                        List<String> attributeNames = new ArrayList<>();
                        for (String attributeName : refreshedAttributes) {
                            for (Map.Entry<ViewAttribute, String> entry : attributeMapping.entrySet()) {
                                if (entry.getValue().equals(model.getClass() + ":" + attributeName)) {
                                    attributeNames.add(String.format("%s:%s", entry.getKey().getNamespace(), entry.getKey().getAttributeName()));
                                }
                            }
                        }
                        if ((refreshedAttributes.length == 0 || attributeNames.size() > 0) && binding.shouldTrigger(ViewContext.this, attributeNames.toArray(new String[]{}))) {
                            binding.applyToView(ViewContext.this);
                        }
                    }
                });
            }
        }
    }
}
