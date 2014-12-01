package com.lotaris.todo.rest.api;

import com.lotaris.todo.model.ToDo;
import com.lotaris.todo.rest.ToDoListResource;
import com.lotaris.todo.rest.ToDoTO;
import com.lotaris.todo.service.IToDoListService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class ToDoListResourceUnitTest {

	@Mock
	private IToDoListService toDoListService;
	@InjectMocks
	private ToDoListResource resource;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getToDoListShouldReturnAllToDos() {
		final ArrayList todos = new ArrayList();
		ToDo todo1 = new ToDo("todo1");
		todo1.setChecked(false);
		todo1.setId(123l);
		todos.add(todo1);
		ToDo todo2 = new ToDo("todo2");
		todo2.setChecked(true);
		todo2.setId(456l);
		todos.add(todo2);
		when(toDoListService.getToDos()).thenReturn(todos);
		
		Response response = resource.getToDoList();
		
		assertEquals(200, response.getStatus());
		verify(toDoListService).getToDos();
		List<ToDoTO> responseEntity = (List<ToDoTO>) response.getEntity();
		assertThat(responseEntity, new BaseMatcher<List<ToDoTO>>() {

			private List<ToDoTO> tos;
			
			@Override
			public boolean matches(Object item) {
				tos = (List<ToDoTO>) item;
				if (tos.size() != 2) {
					return false;
				}
				boolean[] conditions = new boolean[2];
				for (ToDoTO to : tos) {
					if (to.getId() == 123l) {
						conditions[0] = !to.isChecked() && to.getName().equals("todo1");
					} else if (to.getId() == 456l) {
						conditions[1] = to.isChecked() && to.getName().equals("todo2");
					} else {
						return false;
					}
				}
				return conditions[0] && conditions[1];
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("ToDoTO mismatch: received <" + tos.toString() + "> while expecting <" + todos.toString() + ">");
			}
		});
	}
	
	@Test
	public void createShouldCreateAndReturnNewToDo() {
		ToDoTO toCreate = new ToDoTO();
		toCreate.setName("todoToCreate");
		when(toDoListService.addToDo("todoToCreate")).thenReturn(222l);
		
		Response response = resource.create(toCreate);
		
		assertEquals(200, response.getStatus());
		verify(toDoListService).addToDo("todoToCreate");
		ToDoTO responseEntity = (ToDoTO) response.getEntity();
		assertFalse(responseEntity.isChecked());
		assertSame("todoToCreate", responseEntity.getName());
		assertEquals((long) 222l, (long) responseEntity.getId());
	}
	
	@Test
	public void setCheckShouldSetCheckAndReturnOK() {
		doNothing().when(toDoListService).checkToDo(100l);
		
		Response response = resource.setCheck("100");
		
		assertEquals(204, response.getStatus());
		verify(toDoListService).checkToDo(100l);
	}
	
	@Test
	public void setUncheckShouldSetUncheckAndReturnOK() {
		doNothing().when(toDoListService).uncheckToDo(101l);
		
		Response response = resource.setUncheck("101");
		
		assertEquals(204, response.getStatus());
		verify(toDoListService).uncheckToDo(101l);
	}
	
	@Test
	public void cleanupShouldDeleteAllIfTypeIsAllAndReturnTodos() {
		doNothing().when(toDoListService).cleanup(false);
		Response expected = Response.ok().build();
		ToDoListResource spy = spy(resource);
		doReturn(expected).when(spy).getToDoList();
		
		Response response = spy.cleanup("all");
	
		assertSame(expected, response);
		verify(toDoListService).cleanup(false);
	}
	
	@Test
	public void cleanupShouldCleanupCheckedIfTypeIsCompletedAndReturnTodos() {
		doNothing().when(toDoListService).cleanup(true);
		Response expected = Response.ok().build();
		ToDoListResource spy = spy(resource);
		doReturn(expected).when(spy).getToDoList();
		
		Response response = spy.cleanup("completed");
		
		assertSame(expected, response);
		verify(toDoListService).cleanup(true);
	}
	
	@Test
	public void cleanupShouldReturnErrorIfTypeIsNotSet() {
		Response response = resource.cleanup("");
		
		assertEquals(500, response.getStatus());
		verifyZeroInteractions(toDoListService);
	}
}
