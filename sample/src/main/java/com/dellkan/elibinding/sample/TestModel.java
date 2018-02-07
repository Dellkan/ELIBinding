package com.dellkan.elibinding.sample;

import com.dellkan.elibinding.PresentationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestModel extends PresentationModel {
	public String test = "Hello world";
	public String buttonText = "Update";
	public int counter = 0;
	public String twoWayBinding = "";
	public TestModel2 firstLink = new TestModel2();
	public List<ListItem> items = new ArrayList<>(Arrays.asList(
			new ListItem(this, "Text 1", true),
			new ListItem(this, "Text 2", true),
			new ListItem(this, "Text 3", false),
			new ListItem(this, "Text 4", false),
			new ListItem(this, "Text 5", false),
			new ListItem(this, "Text 6", false),
			new ListItem(this, "Text 7", false),
			new ListItem(this, "Text 8", false),
			new ListItem(this, "Text 9", false),
			new ListItem(this, "Text 10", false),
			new ListItem(this, "Text 11", false),
			new ListItem(this, "Text 12", false),
			new ListItem(this, "Text 13", false),
			new ListItem(this, "Text 14", false),
			new ListItem(this, "Text 15", false),
			new ListItem(this, "Text 16", false),
			new ListItem(this, "Text 17", false),
			new ListItem(this, "Text 18", false),
			new ListItem(this, "Text 19", false),
			new ListItem(this, "Text 20", false),
			new ListItem(this, "Text 21", false),
			new ListItem(this, "Text 22", false),
			new ListItem(this, "Text 23", false),
			new ListItem(this, "Text 24", false),
			new ListItem(this, "Text 25", false),
			new ListItem(this, "Text 26", false),
			new ListItem(this, "Text 27", false),
			new ListItem(this, "Text 28", false),
			new ListItem(this, "Text 29", false),
			new ListItem(this, "Text 30", false)
	));

	public void update() {
		test = "The binding seems to work " + Math.random();
		buttonText = "Update again! " + ++counter;
		refresh("test", "buttonText");
	}

	public void deleteChild(ListItem item) {
		items.remove(item);
		refresh("items");
	}
}
