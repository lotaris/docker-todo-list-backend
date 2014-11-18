package com.lotaris.todo.itf;

import com.lotaris.todo.IToDoTestController;
import com.lotaris.j2ee.itf.TestController;
import com.lotaris.rox.client.j2ee.itf.rest.AbstractTestResource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 * Implementation of the resource to start the integration tests
 *
 * @author Laurent Prevost <laurent.prevost@forbes-digital.com>
 */
@Path("start")
@RequestScoped
public class ItfResource extends AbstractTestResource {
	@Inject
	private IToDoTestController testController;

	@Override
	public TestController getController() {
		return testController;
	}

	@Override
	public void parseOptions(String options) {
		// No additional options to parse
	}
}
