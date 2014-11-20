package com.lotaris.todo.service;

import com.lotaris.todo.persistence.IToDoDao;
import com.lotaris.todo.model.ToDo;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Stateless
public class ToDoListService implements IToDoListService {
	
	@EJB
	private IToDoDao toDoEntityManager;

	@Override
	public List<ToDo> getToDos() {
		return toDoEntityManager.findAll();
	}

	@Override
	public Long addToDo(String name) {
		ToDo newToDo = new ToDo(name);
		return toDoEntityManager.createToDo(newToDo);
	}

	@Override
	public void checkToDo(Long id) {
		ToDo toDoToCheck = toDoEntityManager.find(id);
		toDoToCheck.setChecked(true);
		toDoEntityManager.edit(toDoToCheck);
	}

	@Override
	public void uncheckToDo(Long id) {
		ToDo toDoToCheck = toDoEntityManager.find(id);
		toDoToCheck.setChecked(false);
		toDoEntityManager.edit(toDoToCheck);
	}

	@Override
	public void cleanup(boolean onlyCompleted) {
		if (onlyCompleted) {
			toDoEntityManager.deleteCompleted();
		} else {
			toDoEntityManager.deleteAll();
		}
	}
	
}
