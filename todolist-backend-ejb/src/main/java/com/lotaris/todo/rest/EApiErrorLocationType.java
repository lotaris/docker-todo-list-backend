package com.lotaris.todo.rest;

import com.lotaris.jee.validation.IErrorLocationType;

/**
 * Defines the location types for the Api Errors.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public enum EApiErrorLocationType implements IErrorLocationType{

	JSON("json"),
	QUERY_PARAM("queryParam"),
	PATH_PARAM("pathParam"),
	HEADER("header");

	private final String locationType;

	private EApiErrorLocationType(String locationType) {
		this.locationType = locationType;
	}

	@Override
	public String getLocationType() {
		return locationType;
	}

}
