package com.dellkan.elibinding.sample;

import com.dellkan.elibinding.PresentationModel;

public class TestModel extends PresentationModel {
	public String test = "Hello world";
	public String buttonText = "Update";
	public int counter = 0;
	public String twoWayBinding = "";
	public TestModel2 firstLink = new TestModel2();

	public void update() {
		test = "The binding seems to work " + Math.random();
		buttonText = "Update again! " + ++counter;
		refresh("test", "buttonText");
	}
}
