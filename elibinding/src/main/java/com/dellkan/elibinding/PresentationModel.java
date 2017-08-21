package com.dellkan.elibinding;

public abstract class PresentationModel {
    public PresentationModel() {
         ModelChangeHandler.register(this);
    }

    public void refresh(String... attributeName) {
        ModelChangeHandler.refresh(this, attributeName);
    }
}