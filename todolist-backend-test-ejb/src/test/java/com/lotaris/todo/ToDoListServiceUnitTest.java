package com.lotaris.todo;

import com.lotaris.todo.model.ToDo;
import com.lotaris.todo.persistence.IToDoDao;
import com.lotaris.todo.service.ToDoListService;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class ToDoListServiceUnitTest {

	@Mock
	private IToDoDao toDoEntityManager;
	@InjectMocks
	private ToDoListService listService;

	public ToDoListServiceUnitTest() {
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getToDosShouldFindAndReturnAll() {
		ArrayList<ToDo> todos = new ArrayList();
		when(toDoEntityManager.findAll()).thenReturn(todos);

		List<ToDo> res = listService.getToDos();
		assertSame(todos, res);
	}

	@Test
	public void addToDoShouldCreateAndReturnToDo() {
		final Long ID = 123l;
		final String NAME = "aName";
		when(toDoEntityManager.createToDo(any(ToDo.class))).thenReturn(ID);

		Long res = listService.addToDo(NAME);
		assertSame(ID, res);
		verify(toDoEntityManager).createToDo(argThat(new BaseMatcher<ToDo>() {

			private ToDo todo;

			@Override
			public boolean matches(Object item) {
				todo = (ToDo) item;
				return todo.getName().equals(NAME);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Expected ToDo with name '" + NAME + "', but got '" + todo.getName() + "'");
			}
		}));
	}

	@Test
	public void checkToDoShouldFindSetCheckAndPersistToDo() {
		final Long ID = 123l;
		final String NAME = "aName";
		final ToDo TODO = new ToDo(NAME);
		when(toDoEntityManager.find(ID)).thenReturn(TODO);

		listService.checkToDo(ID);

		verify(toDoEntityManager).edit(argThat(new BaseMatcher<ToDo>() {

			private ToDo todo;

			@Override
			public boolean matches(Object item) {
				todo = (ToDo) item;
				assertSame(TODO, todo);
				return todo == TODO && TODO.isChecked();
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Expected ToDo to be checked");
			}
		}));
	}

	@Test
	public void uncheckToDoShouldFindSetUncheckAndPersistToDo() {
		final Long ID = 123l;
		final String NAME = "aName";
		final ToDo TODO = new ToDo(NAME);
		TODO.setChecked(true);
		when(toDoEntityManager.find(ID)).thenReturn(TODO);

		listService.uncheckToDo(ID);

		verify(toDoEntityManager).edit(argThat(new BaseMatcher<ToDo>() {

			private ToDo todo;

			@Override
			public boolean matches(Object item) {
				todo = (ToDo) item;
				assertSame(TODO, todo);
				return todo == TODO && !TODO.isChecked();
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Expected ToDo to be unchecked");
			}
		}));
	}
	
	@Test
	public void cleanupShouldDeleteOnlyCompleted() {
		listService.cleanup(true);
		
		verify(toDoEntityManager).deleteCompleted();
	}
	
	@Test
	public void cleanupShouldDeleteAll() {
		listService.cleanup(false);
		
		verify(toDoEntityManager).deleteAll();
	}
}
