package com.dellkan.elibinding.sample;

import com.dellkan.elibinding.PresentationModel;

public class TestModel extends PresentationModel {
	String test = "Hello world";
	String buttonText = "Update";
	int counter = 0;
	TestModel2 firstLink = new TestModel2();

	public void update() {
		test = "The binding seems to work " + Math.random();
		buttonText = "Update again! " + ++counter;
		refresh("test", "buttonText");
	}
}
