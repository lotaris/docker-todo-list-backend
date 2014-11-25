package com.lotaris.todo.persistence;

import com.lotaris.j2ee.itf.TestGroup;
import com.lotaris.j2ee.itf.annotations.Test;
import com.lotaris.j2ee.itf.annotations.TestSetup;
import com.lotaris.j2ee.itf.annotations.TestSetupType;
import com.lotaris.j2ee.itf.model.Description;
import com.lotaris.todo.model.ToDo;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Stateless
public class ToDoDaoTest implements IToDoDaoTest {
	
	@EJB
	private IToDoDao toDoDao;
	
	private ToDo todo1;
	private Long todo1Id;
	private ToDo todo2;
	private Long todo2Id;
	private ToDo todo3;
	private Long todo3Id;
	private ToDo todo4;
	private Long todo4Id;
	private ToDo todo5;
	private Long todo5Id;
	private ToDo todo6;
	private Long todo6Id;
	private ToDo todo7;
	private Long todo7Id;
	private ToDo todo8;
	private Long todo8Id;
	
	@TestSetup(value = TestSetupType.BEFORE_EACH_OUT_MAIN_TX, index = 0)
	public void before0() {
		todo5 = new ToDo("todo5");
		todo5Id = toDoDao.createToDo(todo5);
		todo6 = new ToDo("todo6");
		todo6.setChecked(true);
		todo6Id = toDoDao.createToDo(todo6);
	}
	
	@TestSetup(value = TestSetupType.BEFORE_EACH_OUT_MAIN_TX, index = 1)
	public void before1() {
		toDoDao.deleteAll();
	}
	
	@TestSetup(value = TestSetupType.BEFORE_EACH_OUT_MAIN_TX, index = 2)
	public void before2() {
		todo7 = new ToDo("todo7");
		todo7Id = toDoDao.createToDo(todo7);
		todo8 = new ToDo("todo8");
		todo8.setChecked(true);
		todo8Id = toDoDao.createToDo(todo8);
	}
	
	@TestSetup(value = TestSetupType.BEFORE_EACH_OUT_MAIN_TX, index = 3)
	public void before3() {
		toDoDao.deleteCompleted();
	}
	
	@TestSetup(value = TestSetupType.BEFORE_EACH_OUT_MAIN_TX, index = 4)
	public void before4() {
		todo1 = new ToDo("todo1");
		todo1Id = toDoDao.createToDo(todo1);
		todo2 = new ToDo("todo2");
		todo2.setChecked(true);
		todo2Id = toDoDao.createToDo(todo2);
		todo3 = new ToDo("todo3");
		todo3Id = toDoDao.createToDo(todo3);
		todo4 = new ToDo("todo4");
		todo4.setChecked(true);
		todo4Id = toDoDao.createToDo(todo4);
	}
	
	@TestSetup(value = TestSetupType.BEFORE_EACH_OUT_MAIN_TX, index = 5)
	public void before5() {
		ToDo toCheck = toDoDao.find(todo3Id);
		toCheck.setChecked(true);
		toDoDao.edit(toCheck);
		
		ToDo toUncheck = toDoDao.find(todo4Id);
		toUncheck.setChecked(false);
		toDoDao.edit(toUncheck);
	}
	
	@TestSetup(value = TestSetupType.AFTER_EACH_OUT_MAIN_TX)
	public void after() {
		toDoDao.remove(todo1);
		toDoDao.remove(todo2);
		toDoDao.remove(todo3);
		toDoDao.remove(todo4);
		toDoDao.remove(todo7);
	}
	
	@Test
	public Description daoShouldFindAll(Description description) {
		List<ToDo> res = toDoDao.findAll();
		
		if (res.size() != 5) {
			return description.fail("DAO findAll should return 5 todos, got " + res.size());
		}
		for (ToDo todo : res) {
			if (Objects.equals(todo.getId(), todo1Id)) {
				if (!todo.getName().equals("todo1") || todo.isChecked()) {
					return description.fail("todo1 is not correctly retrieved from DB");
				}
			} else if (Objects.equals(todo.getId(), todo2Id)) {
				if (!todo.getName().equals("todo2") || !todo.isChecked()) {
					return description.fail("todo2 is not correctly retrieved from DB");
				}
			}
		}
		
		return description.pass();
	}
	
	@Test
	public Description daoShouldEdit (Description description) {
		ToDo res1 = toDoDao.find(todo3Id);
		ToDo res2 = toDoDao.find(todo4Id);
		
		if (!res1.getId().equals(todo3Id) || !res1.getName().equals("todo3") || !res1.isChecked()) {
			return description.fail("todo3 has not been edited correctly.");
		}
		if (!res2.getId().equals(todo4Id) || !res2.getName().equals("todo4") || res2.isChecked()) {
			return description.fail("todo3 has not been edited correctly.");
		}
		
		return description.pass();
	}
	
	@Test
	public Description daoShouldDelete (Description description) {
		if (toDoDao.find(todo5Id) != null || toDoDao.find(todo6Id) != null || toDoDao.find(todo8Id) != null) {
			return description.fail("todos 5, 6 and 8 should have been deleted.");
		}
		if (toDoDao.find(todo7Id) == null) {
			return description.fail("todo7 should not have been deleted.");
		}
		
		return description.pass();
	}

	@Override
	public TestGroup getTestGroup() {
		return this;
	}
	
}
