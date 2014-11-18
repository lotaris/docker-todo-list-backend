package com.lotaris.todo.rest;

import com.lotaris.jee.rest.AbstractResource;
import com.lotaris.jee.validation.ApiErrorResponse;
import com.lotaris.jee.validation.ApiErrorsException;
import com.lotaris.jee.validation.IConstraintConverter;
import com.lotaris.jee.validation.IErrorCode;
import com.lotaris.jee.validation.IErrorLocationType;
import java.lang.annotation.Annotation;

/**
 * Useful utilities for REST resources.
 *
 * @author Laurent Prevost <laurent.prevost@forbes-digital.com>
 */
public class ToDoAbstractResource extends AbstractResource {

	@Override
	protected IConstraintConverter getConstraintConverter() {
		return new IConstraintConverter() {
			@Override
			public IErrorCode getErrorCode(Class<? extends Annotation> annotationType) {
				JsonConstraintConverter constraintConverter = annotationType.getAnnotation(JsonConstraintConverter.class);

				if (constraintConverter != null) {
					return constraintConverter.code();
				}
				else {
					return null;
				}
			}

			@Override
			public IErrorLocationType getErrorLocationType(Class<? extends Annotation> annotationType) {
				JsonConstraintConverter constraintConverter = annotationType.getAnnotation(JsonConstraintConverter.class);

				if (constraintConverter != null) {
					return constraintConverter.locationType();
				}
				else {
					return null;
				}
			}
		};
	}

	/**
	 * Helper method that throws an error if the passed param does not match
	 * the passed pattern.
	 *
	 * @param pattern the pattern the param should match
	 * @param paramValue the value of the parameter to check
	 * @param pathParam the name of the path param
	 * @param errorCode the error code to use if the parameter doesn't match
	 * @throws ApiErrorsException if the value doesn't match the pattern
	 */
	protected void checkUrlParameterPattern(String pattern, String paramValue, String pathParam, EApiErrorCodes errorCode) throws ApiErrorsException {
		checkUrlParameterPattern(pattern, paramValue, pathParam, errorCode, null);
	}

	/**
	 * Helper method that throws an error if the passed param does not match
	 * the passed pattern.
	 *
	 * @param pattern the pattern the param should match
	 * @param paramValue the value of the parameter to check
	 * @param pathParam the name of the path param
	 * @param errorCode the error code to use if the parameter doesn't match
	 * @param errorMessageTemplate the optional error message to use if the parameter doesn't match
	 * @throws ApiErrorsException if the value doesn't match the pattern
	 */
	protected void checkUrlParameterPattern(String pattern, String paramValue, String pathParam, EApiErrorCodes errorCode, String errorMessageTemplate) throws ApiErrorsException {
		if (!paramValue.matches(pattern)) {
			String message = errorMessageTemplate != null ? errorMessageTemplate : "The url parameter: \"%s\" doesn't match its formatting pattern.";
			message = String.format(message, paramValue);
			throw new ApiErrorsException(new ApiErrorResponse(
					message, errorCode,
					EApiErrorLocationType.PATH_PARAM, pathParam));
		}
	}
}
