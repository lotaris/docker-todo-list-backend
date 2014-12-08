package com.lotaris.todo.integration.matchers.finder;

import com.lotaris.junitee.finder.IFinder;
import com.lotaris.todo.model.ToDo;
import com.lotaris.todo.persistence.IToDoDao;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class ToDosFinder implements IFinder {
	
	@EJB
	private IToDoDao toDoDao;
	
	public List<ToDo> findAll() {
		return toDoDao.findAll();
	}
	
	public ToDo find(Long id) {
		return toDoDao.find(id);
	}
	
}
