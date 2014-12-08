package com.lotaris.todo.integration.matchers;

import javax.json.JsonObject;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class ToDoMatcher extends BaseMatcher<JsonObject> {
	
	private final String name;
	private final boolean checked;
	private JsonObject todo;
	
	public ToDoMatcher(String name, boolean checked) {
		this.name = name;
		this.checked = checked;
	}

	@Override
	public boolean matches(Object item) {
		todo = (JsonObject) item;
		return todo.getString("name").equals(name) && todo.getBoolean("checked") == checked && todo.getJsonNumber("id") != null;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Expected name <" + name + ">, checked <" + checked + ">, got <" + todo.toString() + ">");
	}
	
}
