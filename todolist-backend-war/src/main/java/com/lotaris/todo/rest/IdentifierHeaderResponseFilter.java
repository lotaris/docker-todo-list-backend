package com.lotaris.todo.rest;

import java.io.IOException;
import java.util.UUID;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 *
 * @author Francois Vessaz <francois.vessaz@lotaris.com>
 */
public class IdentifierHeaderResponseFilter implements ContainerResponseFilter {

	private static final UUID IDENTIFIER = UUID.randomUUID();
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		responseContext.getHeaders().add("X-Server-Identifier", IDENTIFIER.toString());
	}
	
}
