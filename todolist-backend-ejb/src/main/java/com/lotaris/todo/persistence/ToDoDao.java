package com.lotaris.todo.persistence;

import com.lotaris.todo.model.ToDo;
import javax.ejb.Stateless;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Stateless
public class ToDoDao extends AbstractDao<ToDo> implements IToDoDao {
	public ToDoDao() {
		super(ToDo.class);
	}
}
