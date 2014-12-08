package com.lotaris.todo.integration.matchers;

import java.util.HashMap;
import javax.json.JsonArray;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class ToDoListMatcher extends BaseMatcher<JsonArray> {

	private final HashMap<String, Boolean> expected;
	private JsonArray got;
	private String message;

	public ToDoListMatcher(HashMap expected) {
		this.expected = expected;
	}
	
	@Override
	public boolean matches(Object item) {
		got = (JsonArray) item;
		message = "";
		
		if (expected.size() != got.size()) {
			message += "Expected " + expected.size() + " items, got " + got.size();
			return false;
		}
		
		for (String name : expected.keySet()) {
			ToDoMatcher matcher = new ToDoMatcher(name, expected.get(name));
			boolean isMatching = false;
			for (Object o : got) {
				if (matcher.matches(o)) {
					isMatching = true;
					break;
				}
			}
			if (!isMatching) {
				message += "Missing item: " + name + "->" + expected.get(name).toString() + "\n";
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(message);
	}
	
}
