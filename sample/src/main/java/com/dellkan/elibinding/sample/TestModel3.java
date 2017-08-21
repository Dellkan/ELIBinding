package com.dellkan.elibinding.sample;

import com.dellkan.elibinding.PresentationModel;

public class TestModel3 extends PresentationModel {
    public String testString = "123";
    public void testMethod() {
        testString = testString + (Integer.parseInt(testString.substring(testString.length()-1)) + 1);
        refresh("testString");
    }
}
