package com.lotaris.todo.persistence;

import com.lotaris.todo.model.ToDo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Local
public interface IToDoDao {

	void create(ToDo toDoEntity);
	
	Long createToDo(ToDo entity);

	void edit(ToDo toDoEntity);

	void remove(ToDo toDoEntity);

	ToDo find(Object id);

	List<ToDo> findAll();

	List<ToDo> findRange(int[] range);

	int count();
	
	void deleteAll();
	
	void deleteCompleted();
	
}
