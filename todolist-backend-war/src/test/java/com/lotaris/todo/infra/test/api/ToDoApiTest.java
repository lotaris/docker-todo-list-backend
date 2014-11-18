package com.lotaris.todo.infra.test.api;

import com.lotaris.api.test.AbstractApiTest;
import com.lotaris.api.test.client.ApiUriBuilder;
import com.lotaris.api.test.client.IApiTestClientConfiguration;
import com.lotaris.api.test.headers.IApiHeaderConfiguratorLocator;
import com.lotaris.junitee.finder.FinderManager;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.rules.TestRule;

/**
 * Useful functionality for API tests.
 *
 * <p>An API client is automatically instantiated before each test and closed after each test.
 *
 * <p>Methods such as {@link #getResource(java.lang.String[])} are provided for GET, POST, PUT,
 * PATCH and DELETE. They perform the request (with a body for POST, PUT and PATCH) and return a API
 * test response.</p>
 *
 * <p>A JsonPath asserter (see https://code.google.com/p/json-path/) is returned by
 * <tt>withJson</tt> methods. They can be used to run assertions on the response body.</p>
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public abstract class ToDoApiTest extends AbstractApiTest {
	//<editor-fold defaultstate="collapsed" desc="Resource Bundle">
	/**
	 * Configuration bundle containing the server's base URL.
	 */
	private static final Configuration CONFIGURATION;

	static {
		CONFIGURATION = Configuration.getInstance();
	}
	//</editor-fold>
	/**
	 * API data generator that also works with the API rules
	 */
	protected ApiDataGeneratorRule dataGeneratorManager;
	/**
	 * Finder manager rule
	 */
	protected FinderManager finderManager;

	@Override
	protected void preBuild() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");
		dataGeneratorManager = new ApiDataGeneratorRule(emf);
		finderManager = new FinderManager(emf);
	}

	@Override
	protected String getEntryPoint() {
		return CONFIGURATION.getBaseUrlExt();
	}

	@Override
	protected IApiTestClientConfiguration getClientConfiguration() {
		return CONFIGURATION;
	}

	@Override
	protected IApiHeaderConfiguratorLocator getHeaderConfiguratorLocator() {
		return dataGeneratorManager;
	}

	@Override
	protected List<TestRule> rulesAfterClientRules() {
		return Arrays.asList(new TestRule[]{dataGeneratorManager});
	}

	@Override
	protected List<TestRule> rulesAfterHeaderConfigurationRule() {
		return Arrays.asList(new TestRule[]{finderManager});
	}

	protected ApiUriBuilder testUri(String... path) {
		return new ApiUriBuilder(CONFIGURATION.getTestBaseUrlExt()).path("test").path(path);
	}
}
