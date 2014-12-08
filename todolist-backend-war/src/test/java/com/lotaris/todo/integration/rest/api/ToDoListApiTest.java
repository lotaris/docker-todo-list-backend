package com.lotaris.todo.integration.rest.api;

import com.lotaris.api.test.client.ApiTestResponse;
import com.lotaris.junitee.finder.Finder;
import com.lotaris.junitee.generator.DataGenerator;
import com.lotaris.todo.infra.test.api.ToDoApiTest;
import com.lotaris.todo.integration.datagenerator.ToDosGenerator;
import com.lotaris.todo.integration.matchers.ToDoListMatcher;
import com.lotaris.todo.integration.matchers.ToDoMatcher;
import com.lotaris.todo.integration.matchers.finder.ToDosFinder;
import com.lotaris.todo.model.ToDo;
import java.util.HashMap;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class ToDoListApiTest extends ToDoApiTest {

	@Test
	@DataGenerator(ToDosGenerator.class)
	public void getToDoList() {
		HashMap<String, Boolean> expected = new HashMap();
		expected.put("todo_uncheck_0", false);
		expected.put("todo_uncheck_1", false);
		expected.put("todo_uncheck_2", false);
		expected.put("todo_uncheck_3", false);
		expected.put("todo_uncheck_4", false);
		expected.put("todo_check_0", true);
		expected.put("todo_check_1", true);
		expected.put("todo_check_2", true);
		expected.put("todo_check_3", true);
		expected.put("todo_check_4", true);

		ApiTestResponse response = getResource(uri("api/todos"));

		assertEquals(200, response.getStatus());
		assertEquals(36, response.getHeaderString("x-server-identifier").length());
		assertThat(response.getResponseAsJsonArray(), is(new ToDoListMatcher(expected)));
	}

	@Test
	@Finder(ToDosFinder.class)
	public void createToDo() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("name", "test_object");

		ApiTestResponse response = postResource(builder.build(), uri("api/todos"));

		assertEquals(200, response.getStatus());
		assertEquals(36, response.getHeaderString("x-server-identifier").length());
		assertThat(response.getResponseAsJsonObject(), is(new ToDoMatcher("test_object", false)));
		ToDosFinder finder = finderManager.getFinder(ToDosFinder.class);
		List<ToDo> persisted = finder.findAll();
		assertEquals(1, persisted.size());
		assertEquals("test_object", persisted.get(0).getName());
		assertFalse(persisted.get(0).isChecked());
	}

	@Test
	@DataGenerator(ToDosGenerator.class)
	@Finder(ToDosFinder.class)
	public void setToDoCheck() {
		ToDosGenerator generator = dataGeneratorManager.getDataGenerator(ToDosGenerator.class);
		Long toCheckId = generator.getUncheckedIds().get(0);

		ApiTestResponse response = postResource((JsonStructure) null, uri("api/todos/" + toCheckId + "/check"));

		assertEquals(204, response.getStatus());
		assertEquals(36, response.getHeaderString("x-server-identifier").length());
		ToDosFinder finder = finderManager.getFinder(ToDosFinder.class);
		ToDo persisted = finder.find(toCheckId);
		assertTrue(persisted.isChecked());
	}

	@Test
	@DataGenerator(ToDosGenerator.class)
	@Finder(ToDosFinder.class)
	public void setToDoUncheck() {
		ToDosGenerator generator = dataGeneratorManager.getDataGenerator(ToDosGenerator.class);
		Long toUncheckId = generator.getCheckedIds().get(0);

		ApiTestResponse response = postResource((JsonStructure) null, uri("api/todos/" + toUncheckId + "/uncheck"));

		assertEquals(204, response.getStatus());
		assertEquals(36, response.getHeaderString("x-server-identifier").length());
		ToDosFinder finder = finderManager.getFinder(ToDosFinder.class);
		ToDo persisted = finder.find(toUncheckId);
		assertFalse(persisted.isChecked());
	}

	@Test
	@DataGenerator(ToDosGenerator.class)
	@Finder(ToDosFinder.class)
	public void cleanupChecked() {
		ApiTestResponse response = deleteResource(uri("api/todos").queryParam("type", "completed"));

		assertEquals(200, response.getStatus());
		assertEquals(36, response.getHeaderString("x-server-identifier").length());
		ToDosFinder finder = finderManager.getFinder(ToDosFinder.class);
		List<ToDo> persisted = finder.findAll();
		assertEquals(5, persisted.size());
	}

	@Test
	@DataGenerator(ToDosGenerator.class)
	@Finder(ToDosFinder.class)
	public void cleanupAll() {
		ApiTestResponse response = deleteResource(uri("api/todos").queryParam("type", "all"));

		assertEquals(200, response.getStatus());
		assertEquals(36, response.getHeaderString("x-server-identifier").length());
		ToDosFinder finder = finderManager.getFinder(ToDosFinder.class);
		List<ToDo> persisted = finder.findAll();
		assertEquals(0, persisted.size());
	}
	
	@Test
	public void cleanupError() {
		ApiTestResponse response = deleteResource(uri("api/todos"));

		assertEquals(500, response.getStatus());
		assertEquals(36, response.getHeaderString("x-server-identifier").length());
	}
}
