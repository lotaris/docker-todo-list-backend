package com.lotaris.todo;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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
@Path("todolist")
public class ToDoListResource {

	@Context
	private UriInfo context;
	
	private ToDoListBeanLocal toDoListBean;
	
	/**
	 * Creates a new instance of ToDoListResource
	 */
	public ToDoListResource() {
	}

	@GET
  @Produces(MediaType.APPLICATION_JSON)
	public Response getToDoList() {
		List<ToDoEntity> toDoEntities = toDoListBean.getToDos();
		List<ToDoTO> toDoTOs = new ArrayList();
		for (ToDoEntity toDoEntity : toDoEntities) {
			toDoTOs.add(new ToDoTO(toDoEntity.getId(), toDoEntity.getName(), toDoEntity.isChecked()));
		}
		return Response.ok(toDoTOs).build();
	}

	@PUT
  @Consumes(MediaType.APPLICATION_JSON)
	public Response create(ToDoTO newToDoTO) {
		toDoListBean.addToDo(newToDoTO.getName());
		return Response.ok().build();
	}
	
	@GET
  @Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/check")
	public Response setCheck(@PathParam("id") String id) {
		toDoListBean.checkToDo(Long.parseLong(id));
		return Response.ok().build();
	}
	
	@GET
  @Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/uncheck")
	public Response setUncheck(@PathParam("id") String id) {
		toDoListBean.uncheckToDo(Long.parseLong(id));
		return Response.ok().build();
	}

}
