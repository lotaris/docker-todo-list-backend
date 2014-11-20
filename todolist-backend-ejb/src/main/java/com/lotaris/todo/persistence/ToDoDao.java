package com.lotaris.todo.persistence;

import com.lotaris.todo.model.ToDo;
import javax.ejb.Stateless;

import static com.lotaris.todo.model.QueryNames.*;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Stateless
public class ToDoDao extends AbstractDao<ToDo> implements IToDoDao {
	public ToDoDao() {
		super(ToDo.class);
	}
	
	@Override
	public Long createToDo(ToDo entity) {
		create(entity);
		em.flush();
		return entity.getId();
	}

	@Override
	public void deleteAll() {
		em.createNamedQuery(DELETE_ALL_TODOS).executeUpdate();
	}

	@Override
	public void deleteCompleted() {
		em.createNamedQuery(DELETE_COMPLETED_TODOS).executeUpdate();
	}
}
