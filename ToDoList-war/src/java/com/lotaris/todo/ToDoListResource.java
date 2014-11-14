/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lotaris.todo;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
@Path("todolist")
public class ToDoListResource {

	@Context
	private UriInfo context;

	/**
	 * Creates a new instance of ToDoListResource
	 */
	public ToDoListResource() {
	}

	/**
	 * Retrieves representation of an instance of com.lotaris.todo.ToDoListResource
	 * @return an instance of java.lang.String
	 */
	@GET
  @Produces("application/json")
	public String getJson() {
		return "{\"test\" : 123}";
	}

	/**
	 * PUT method for updating or creating an instance of ToDoListResource
	 * @param content representation for the resource
	 * @return an HTTP response with content of the updated or created resource.
	 */
	@PUT
  @Consumes("application/json")
	public void putJson(String content) {
	}
}
