package com.lotaris.todo;

import com.lotaris.j2ee.itf.AbstractTestController;
import com.lotaris.todo.persistence.IToDoDaoTest;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ToDoTestController extends AbstractTestController implements IToDoTestController {
	
	@EJB
	public IToDoDaoTest toDoDaoTest;

}
