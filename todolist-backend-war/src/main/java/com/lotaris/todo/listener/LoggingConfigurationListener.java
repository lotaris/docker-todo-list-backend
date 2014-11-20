package com.lotaris.todo.listener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Logging Configuration listener allows the application server
 * to load our log4j configuration. A context-param must be defined
 * in the web.xml with the name of the logging configuration file.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class LoggingConfigurationListener implements ServletContextListener {
	private static final String CONFIG_KEY = "loggingConfigurationFile";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();

		// Retrieve the value of the web.xml configuration
		String loggingConfigurationFileName = servletContext.getInitParameter(CONFIG_KEY);

		// Check if the configuration file value exists
		if (loggingConfigurationFileName == null || "".equalsIgnoreCase(loggingConfigurationFileName)) {
			throw new IllegalArgumentException("The log configuration file is not defined in web.xml");
		}

		try {
			// Load the XML configuration file
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(loggingConfigurationFileName);

			LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

			// Create the configuration
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			configurator.doConfigure(is);
		}
		catch (JoranException je) {
			throw new IllegalArgumentException(je);
		}

		// Try the logging
		Logger logger;
		logger = LoggerFactory.getLogger(LoggingConfigurationListener.class.getName());
		logger.info("Logging configuration resource file loaded: " + loggingConfigurationFileName);
	}
}
