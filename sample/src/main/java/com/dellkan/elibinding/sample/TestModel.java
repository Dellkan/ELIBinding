package com.dellkan.elibinding.sample;

import com.dellkan.elibinding.PresentationModel;

import java.util.Arrays;
import java.util.List;

public class TestModel extends PresentationModel {
	public String test = "Hello world";
	public String buttonText = "Update";
	public int counter = 0;
	public String twoWayBinding = "";
	public TestModel2 firstLink = new TestModel2();
	public List<ListItem> items = Arrays.asList(
			new ListItem("Text 1", true),
			new ListItem("Text 2", true),
			new ListItem("Text 3", false),
			new ListItem("Text 4", false),
			new ListItem("Text 5", false),
			new ListItem("Text 6", false),
			new ListItem("Text 7", false),
			new ListItem("Text 8", false),
			new ListItem("Text 9", false),
			new ListItem("Text 10", false),
			new ListItem("Text 11", false),
			new ListItem("Text 12", false),
			new ListItem("Text 13", false),
			new ListItem("Text 14", false),
			new ListItem("Text 15", false),
			new ListItem("Text 16", false),
			new ListItem("Text 17", false),
			new ListItem("Text 18", false),
			new ListItem("Text 19", false),
			new ListItem("Text 20", false),
			new ListItem("Text 21", false),
			new ListItem("Text 22", false),
			new ListItem("Text 23", false),
			new ListItem("Text 24", false),
			new ListItem("Text 25", false),
			new ListItem("Text 26", false),
			new ListItem("Text 27", false),
			new ListItem("Text 28", false),
			new ListItem("Text 29", false),
			new ListItem("Text 39", false)
	);

	public void update() {
		test = "The binding seems to work " + Math.random();
		buttonText = "Update again! " + ++counter;
		refresh("test", "buttonText");
	}
}
