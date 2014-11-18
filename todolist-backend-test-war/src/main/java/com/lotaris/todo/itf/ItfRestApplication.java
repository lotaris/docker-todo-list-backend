package com.lotaris.todo.itf;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rest application for integration tests
 *
 * @author Laurent Prevost <laurent.prevost@forbes-digital.com>
 */
@ApplicationPath("itf")
public class ItfRestApplication extends Application {
	private static final Logger LOG = LoggerFactory.getLogger(ItfRestApplication.class);

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();

		try {
			Class jsonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
			resources.add(jsonProvider);
		}
		catch (ClassNotFoundException ex) {
			LOG.error("Unable to configure the Rest application", ex);
		}

		addRestResourceClasses(resources);

		return resources;
	}

	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(ItfResource.class);
	}
}
