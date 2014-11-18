package com.lotaris.todo.rest.test;

import com.lotaris.jee.rest.AbstractRestApplication;
import com.lotaris.jee.rest.mappers.MapperMappingDefinition;
import com.lotaris.jee.rest.providers.CatchAllExceptionMapper;
import com.lotaris.jee.rest.providers.EOFExceptionMapper;
import com.lotaris.jee.rest.providers.JsonMappingExceptionMapper;
import com.lotaris.jee.rest.providers.JsonParseExceptionMapper;
import com.lotaris.jee.rest.providers.NotAcceptableExceptionMapper;
import com.lotaris.jee.rest.providers.NotAllowedExceptionMapper;
import com.lotaris.jee.rest.providers.NotFoundExceptionMapper;
import com.lotaris.jee.rest.providers.NotSupportedExceptionMapper;
import com.lotaris.jee.rest.providers.UnrecognizedPropertyExceptionMapper;
import com.lotaris.todo.rest.EApiErrorCodes;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 * Rest application to expose resources that we want to use for testing.
 *
 * @author Laurent Prevost <laurent.prevost@forbes-digital.com>
 */
@ApplicationPath("test")
public class TestRestApplication extends AbstractRestApplication {
	@Override
	public Set<Object> getSingletons() {
		final Set<Object> singletons = new HashSet<>(1);
		singletons.add(new JacksonJsonProvider());
		return singletons;
	}

	@Override
	protected String[] getPackages() {
		return new String[] { this.getClass().getPackage().getName() };
	}

	@Override
	protected MapperMappingDefinition[] retrieveMappersConfiguration() {
		return map(
				// Standards exception mappers bound to API Error codes from App
			def(CatchAllExceptionMapper .class, EApiErrorCodes .SERVER_UNEXPECTED),
			def(EOFExceptionMapper.class, EApiErrorCodes.REQUEST_END_OF_INPUT),
			def(JsonMappingExceptionMapper.class, EApiErrorCodes.REQUEST_BAD_JSON_VALUE_TYPE),
			def(JsonParseExceptionMapper.class, EApiErrorCodes.REQUEST_INVALID_JSON),
			def(NotAcceptableExceptionMapper.class, EApiErrorCodes.REQUEST_UNACCEPTABLE_MEDIA_TYPE),
			def(NotAllowedExceptionMapper.class, EApiErrorCodes.REQUEST_METHOD_NOT_ALLOWED),
			def(NotFoundExceptionMapper .class, EApiErrorCodes.REQUEST_RESOURCE_NOT_FOUND),
			def(NotSupportedExceptionMapper.class, EApiErrorCodes.REQUEST_UNSUPPORTED_MEDIA_TYPE),
			def(UnrecognizedPropertyExceptionMapper.class, EApiErrorCodes.REQUEST_UNKNOWN_JSON_PROPERTY)

			// Custom exception mapper bound to API Error codes from App
		);
	}
}
