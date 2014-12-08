package com.lotaris.todo.integration.datagenerator;

import com.lotaris.junitee.generator.IDataGenerator;
import com.lotaris.todo.model.ToDo;
import com.lotaris.todo.persistence.IToDoDao;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class ToDosGenerator implements IDataGenerator {

	@EJB
	private IToDoDao toDoDao;
	private final ArrayList uncheckedIds;
	private final ArrayList checkedIds;

	public ToDosGenerator() {
		checkedIds = new ArrayList();
		uncheckedIds = new ArrayList();
	}
	
	@Override
	public void generate() {
		for (int i=0; i<5; i++) {
			ToDo todo = new ToDo("todo_uncheck_" + i);
			uncheckedIds.add(toDoDao.createToDo(todo));
		}
		for (int i=0; i<5; i++) {
			ToDo todo = new ToDo("todo_check_" + i);
			todo.setChecked(true);
			checkedIds.add(toDoDao.createToDo(todo));
		}
	}

	@Override
	public void cleanup() {
		toDoDao.deleteAll();
	}
	
	public List<Long> getCheckedIds() {
		return checkedIds;
	}
	
	public List<Long> getUncheckedIds() {
		return uncheckedIds;
	}
	
}
