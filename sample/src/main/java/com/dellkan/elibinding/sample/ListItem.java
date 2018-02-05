package com.dellkan.elibinding.sample;

import com.dellkan.elibinding.PresentationModel;

public class ListItem extends PresentationModel {
    public String text1;
    public boolean value2;

    public ListItem(String text1, boolean value2) {
        this.text1 = text1;
        this.value2 = value2;
    }
}
