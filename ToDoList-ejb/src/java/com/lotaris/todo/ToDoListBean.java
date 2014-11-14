package com.lotaris.todo;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Stateless
public class ToDoListBean implements ToDoListBeanLocal {
	
	@EJB
	private ToDoEntityFacadeLocal toDoEntityManager;

	@Override
	public List<ToDoEntity> getToDos(boolean isChecked) {
		return toDoEntityManager.findAll();
	}

	@Override
	public void addToDo(String name) {
		ToDoEntity newToDo = new ToDoEntity(name);
		toDoEntityManager.create(newToDo);
	}

	@Override
	public void checkToDo(Long id) {
		ToDoEntity toDoToCheck = toDoEntityManager.find(id);
		toDoToCheck.setChecked(true);
		toDoEntityManager.edit(toDoToCheck);
	}

	@Override
	public void uncheckToDo(Long id) {
		ToDoEntity toDoToCheck = toDoEntityManager.find(id);
		toDoToCheck.setChecked(false);
		toDoEntityManager.edit(toDoToCheck);
	}
	
}
