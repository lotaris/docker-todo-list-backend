package com.lotaris.todo.infra.test.api;

import com.lotaris.api.test.headers.IApiHeaderConfigurator;
import com.lotaris.api.test.headers.IApiHeaderConfiguratorLocator;
import com.lotaris.junitee.generator.DataGeneratorManager;
import com.lotaris.junitee.generator.IDataGenerator;
import javax.persistence.EntityManagerFactory;

/**
 * The data generator rule extended to offer the possibility to retrieve generators
 * and use as api header configurators.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class ApiDataGeneratorRule extends DataGeneratorManager implements IApiHeaderConfiguratorLocator {
	/**
	 * Constructor
	 *
	 * @param entityManagerFactory The entity manager factory
	 */
	public ApiDataGeneratorRule(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	@Override
	public IApiHeaderConfigurator getHeaderConfigurator(Class<? extends IApiHeaderConfigurator> cl) {
		try {
			return (IApiHeaderConfigurator) getDataGenerator(cl.asSubclass(IDataGenerator.class));
		}
		catch (ClassCastException cce) {
			throw new IllegalArgumentException(cl.getCanonicalName() + " must implement " + IDataGenerator.class.getCanonicalName());
		}
	}
}
