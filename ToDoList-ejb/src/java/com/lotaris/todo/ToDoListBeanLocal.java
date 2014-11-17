package com.lotaris.todo;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Local
public interface ToDoListBeanLocal {
	
	List<ToDoEntity> getToDos();
	
	void addToDo(String name);
	
	void checkToDo(Long id);
	
	void uncheckToDo(Long id);
	
}
