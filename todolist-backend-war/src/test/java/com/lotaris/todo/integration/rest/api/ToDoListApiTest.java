package com.lotaris.todo.integration.rest.api;

import com.lotaris.api.test.client.ApiTestResponse;
import com.lotaris.todo.infra.test.api.ToDoApiTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class ToDoListApiTest extends ToDoApiTest {
	
	@Test
	public void getToDoList() {
		ApiTestResponse response = getResource(uri("api/todos"));
		
		assertEquals(200, response.getStatus());
	}
	
}
