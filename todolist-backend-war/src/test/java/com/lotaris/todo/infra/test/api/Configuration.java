package com.lotaris.todo.infra.test.api;

import com.lotaris.api.test.client.IApiTestClientConfiguration;
import java.util.ResourceBundle;

/**
 * Configuration class to handle configuration values.
 *
 * @author @author Laurent Prevost <laurent.prevost@forbes-digital.com>
 */
public class Configuration implements IApiTestClientConfiguration {

	private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

	private static final String BASE_URL_EXT = "todolist.base.url.ext";
	private static final String PROXY_ENABLED = "todolist.proxy.enabled";
	private static final String PROXY_HOST = "todolist.proxy.host";
	private static final String PROXY_PORT = "todolist.proxy.port";
	private static final String PROXY_EXCEPTIONS = "todolist.proxy.exceptions";

	private static final Configuration instance = new Configuration();

	private Configuration() {}

	public static Configuration getInstance() {
		return instance;
	}

	/**
	 * @return The base URL external
	 */
	public String getBaseUrlExt() {
		return CONFIG.getString(BASE_URL_EXT);
	}

	/**
	 * @return The base test URL external
	 */
	public String getTestBaseUrlExt() {
		return getBaseUrlExt();
	}

	@Override
	public boolean isProxyEnabled() {
		return Boolean.parseBoolean(CONFIG.getString(PROXY_ENABLED));
	}

	@Override
	public String getProxyHost() {
		return CONFIG.getString(PROXY_HOST);
	}

	@Override
	public int getProxyPort() {
		return Integer.parseInt(CONFIG.getString(PROXY_PORT));
	}

	@Override
	public String[] getProxyExceptions() {
		return CONFIG.getString(PROXY_EXCEPTIONS).split(",");
	}
}
