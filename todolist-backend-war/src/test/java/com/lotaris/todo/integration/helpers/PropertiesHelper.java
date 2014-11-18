package com.lotaris.todo.integration.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper for properties
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class PropertiesHelper {
	public static  Map<String, String> getFiftyOneProperties(){
		Map<String, String> properties = new HashMap<>();
		String key="Key";
		String value="Value";
		for(int i=0;i<=50;i++){
			properties.put(key + i, value + i);
		}
		return properties;
	}
}
