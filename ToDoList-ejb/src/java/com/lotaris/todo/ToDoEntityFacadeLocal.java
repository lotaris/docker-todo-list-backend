package com.lotaris.todo;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Local
public interface ToDoEntityFacadeLocal {

	void create(ToDoEntity toDoEntity);

	void edit(ToDoEntity toDoEntity);

	void remove(ToDoEntity toDoEntity);

	ToDoEntity find(Object id);

	List<ToDoEntity> findAll();

	List<ToDoEntity> findRange(int[] range);

	int count();
	
}
