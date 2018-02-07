package com.dellkan.elibinding.sample;

import com.dellkan.elibinding.PresentationModel;

public class ListItem extends PresentationModel {
    public String text1;
    public boolean value2;
    private TestModel parent;

    public ListItem(TestModel parent, String text1, boolean value2) {
        this.parent = parent;
        this.text1 = text1;
        this.value2 = value2;
    }

    public void setValue2(boolean toggle) {
        this.value2 = toggle;
    }

    public void delete() {
        parent.deleteChild(this);
    }
}
