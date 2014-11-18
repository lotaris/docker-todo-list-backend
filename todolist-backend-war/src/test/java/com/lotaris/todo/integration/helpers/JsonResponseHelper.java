package com.lotaris.todo.integration.helpers;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Utility methods to parse JSON response and make assertions on them.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class JsonResponseHelper {
	protected static class JSONObjectMatcher extends BaseMatcher<JSONObject> {

		private int size = 0;
		private boolean sizeMatches;

		public JSONObjectMatcher withSize(int size) {
			this.size = size;
			return this;
		}

		@Override
		public boolean matches(Object item) {
			JSONObject jo = (JSONObject) item;
			if (jo != null && jo.entrySet() != null) {
				sizeMatches = size != 0 ? size == jo.entrySet().size() : jo.entrySet().isEmpty();
				return sizeMatches;
			}
			return size == 0;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("Expected a JSONObject with " + size + " properties.");
		}
	}

	protected static class JSONArrayMatcher extends BaseMatcher<JSONArray> {

		private int size = 0;
		private boolean sizeMatches;

		public JSONArrayMatcher withSize(int size) {
			this.size = size;
			return this;
		}

		@Override
		public boolean matches(Object item) {
			JSONArray ja = (JSONArray) item;
			if (ja != null) {
				sizeMatches = size != 0 ? size == ja.size() : ja.isEmpty();
				return sizeMatches;
			}
			return size == 0;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("Expected a JSONArray with " + size + " entries.");
		}
	}
}
