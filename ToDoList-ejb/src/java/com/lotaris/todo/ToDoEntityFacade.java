package com.lotaris.todo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Stateless
public class ToDoEntityFacade extends AbstractFacade<ToDoEntity> implements ToDoEntityFacadeLocal {
	@PersistenceContext(unitName = "ToDoList-ejbPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ToDoEntityFacade() {
		super(ToDoEntity.class);
	}
	
}
