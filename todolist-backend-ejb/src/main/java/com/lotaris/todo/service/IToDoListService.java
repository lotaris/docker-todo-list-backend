package com.lotaris.todo.service;

import com.lotaris.todo.model.ToDo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Local
public interface IToDoListService {
	
	List<ToDo> getToDos();
	
	Long addToDo(String name);
	
	void checkToDo(Long id);
	
	void uncheckToDo(Long id);
	
}
