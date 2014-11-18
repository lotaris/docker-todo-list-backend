package com.lotaris.todo.rest;

import com.lotaris.todo.service.IToDoListService;
import com.lotaris.todo.model.ToDo;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Path("list")
@RequestScoped
public class ToDoListResource extends ToDoAbstractResource {
	@Inject
	private IToDoListService toDoListService;
	
	/**
	 * Creates a new instance of ToDoListResource
	 */
	public ToDoListResource() {
	}

	@GET
  @Produces(MediaType.APPLICATION_JSON)
	public Response getToDoList() {
		List<ToDo> toDoEntities = toDoListService.getToDos();
		List<ToDoTO> toDoTOs = new ArrayList();
		for (ToDo toDoEntity : toDoEntities) {
			toDoTOs.add(new ToDoTO(toDoEntity.getId(), toDoEntity.getName(), toDoEntity.isChecked()));
		}
		return Response.ok(toDoTOs).build();
	}

	@PUT
  @Consumes(MediaType.APPLICATION_JSON)
	public Response create(ToDoTO newToDoTO) {
		toDoListService.addToDo(newToDoTO.getName());
		return Response.ok().build();
	}
	
	@GET
	@Path("{id}/check")
  @Consumes(MediaType.APPLICATION_JSON)
	public Response setCheck(@PathParam("id") String id) {
		toDoListService.checkToDo(Long.parseLong(id));
		return Response.ok().build();
	}
	
	@GET
	@Path("{id}/uncheck")
  @Consumes(MediaType.APPLICATION_JSON)
	public Response setUncheck(@PathParam("id") String id) {
		toDoListService.uncheckToDo(Long.parseLong(id));
		return Response.ok().build();
	}

}
