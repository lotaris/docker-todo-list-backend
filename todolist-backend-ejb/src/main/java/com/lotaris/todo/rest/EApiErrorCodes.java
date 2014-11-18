package com.lotaris.todo.rest;

import com.lotaris.jee.validation.IErrorCode;

/**
 * Identification codes for API errors. Each error message returned by the API should have its
 * corresponding code that can be used to identify or internationalize the error. These codes are
 * more detailed than HTTP status codes, allowing to pinpoint the cause of the error more
 * accurately.
 *
 * <h2>Error Code Ranges</h2>
 *
 * <p>Errors are categorized into ranges for convenience. New errors <strong>MUST</strong> be added
 * at the end of a range. Existing codes <strong>MUST NOT</strong> be changed. Codes of deprecated
 * errors <strong>MUST NOT</strong> be reused.</p>
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public enum EApiErrorCodes implements IErrorCode {

	/*
	 * Low-level request errors:
	 * - resource not found;
	 * - invalid request (empty body, bad HTTP verb, unsupported media type, etc);
	 * - invalid JSON request (syntax error, unknown properties, bad value types, etc);
	 * - authentication errors (bad credentials, signature mismatch, etc).
	 */

	//<editor-fold defaultstate="collapsed" desc="Request Errors (10000-19999)">
	/**
	 * Request was interrupted or body is empty.
	 */
	REQUEST_END_OF_INPUT(10000, EApiHttpStatusCodes.BAD_REQUEST),
	/**
	 * JSON request body is syntactically invalid.
	 */
	REQUEST_INVALID_JSON(10001, EApiHttpStatusCodes.BAD_REQUEST),
	/**
	 * JSON request body contains an unknown property.
	 */
	REQUEST_UNKNOWN_JSON_PROPERTY(10002, EApiHttpStatusCodes.BAD_REQUEST),
	/**
	 * JSON request body is of the wrong type or contains a property of the wrong type.
	 */
	REQUEST_BAD_JSON_VALUE_TYPE(10003, EApiHttpStatusCodes.BAD_REQUEST),
	/**
	 * The resource identified by the request URI does not exist.
	 */
	REQUEST_RESOURCE_NOT_FOUND(10004, EApiHttpStatusCodes.NOT_FOUND),
	/**
	 * The resource does not support the HTTP verb of the request.
	 */
	REQUEST_METHOD_NOT_ALLOWED(10005, EApiHttpStatusCodes.METHOD_NOT_ALLOWED),
	/**
	 * The resource cannot respond with the media type requested in the Accept header.
	 */
	REQUEST_UNACCEPTABLE_MEDIA_TYPE(10006, EApiHttpStatusCodes.NOT_ACCEPTABLE),
	/**
	 * The resource cannot consume the media type specified in the Content-Type header.
	 */
	REQUEST_UNSUPPORTED_MEDIA_TYPE(10007, EApiHttpStatusCodes.UNSUPPORTED_MEDIA_TYPE),
	//</editor-fold>
	/*
	 * Generic errors:
	 * - engine-specific errors (including validation);
	 * - generic validation errors (e.g. not null constraints);
	 * - generic parameter errors (e.g. pagination).
	 */
	//<editor-fold defaultstate="collapsed" desc="Business Errors (20000-29999)">
	/**
	 * A required value is missing or null.
	 */
	GENERIC_NOT_NULL(20000, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * A value was given for a header, parameter or property that must not be included in that request.
	 */
	GENERIC_NULL(20001, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * A string is either shorter or longer than its documented length constraints.
	 */
	GENERIC_BAD_STRING_LENGTH(20002, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * A string doesn't match its documented pattern constraint.
	 */
	GENERIC_BAD_FORMAT(20003, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * No resource was found corresponding to the given reference.
	 */
	GENERIC_REFERENCE_NOT_FOUND(20004, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * The request object has an invalid empty value.
	 */
	GENERIC_NOT_EMPTY(20005, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * The given value is already used by another resource and must be unique.
	 */
	GENERIC_ALREADY_EXISTS(20006, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * A list contains duplicate values which is not allowed by the documented constraints.
	 */
	GENERIC_DUPLICATES_IN_LIST(20007, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * An integer is smaller or greater than its documented range constraint.
	 */
	GENERIC_VALUE_OUT_OF_BOUNDS(20008, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * A value is of the wrong type (e.g. string instead of integer).
	 */
	GENERIC_BAD_VALUE_TYPE(20009, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * The value is read-only and cannot be changed.
	 */
	GENERIC_CANNOT_BE_CHANGED(20010, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * The number of elements in collection is not in the range specified in the resource definition.
	 */
	COLLECTION_SIZE_OUT_OF_RANGE(20011, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	//</editor-fold>

	/*
	 * Business Error Codes:
	 * - resource-specific errors
	 */
	//<editor-fold defaultstate="collapsed" desc="Business Errors (40000-49999)">
	// PAYMENT PROCESSORS
	/**
	 * The Payment Processor is not available, possibly due to missing configuration or third party server being unavailable.
	 */
	BUSINESS_PAYMENT_PROCESSOR_NOT_AVAILABLE(40000, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * The checkout is not possible with the currency and the selected Payment Processor.
	 */
	BUSINESS_PAYMENT_PROCESSOR_UNSUPPORTED_CURRENCY(40001, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),

	// USERS
	/**
	 * User cannot be created because its password does not contain both lowercase and
	 * uppercase characters.
	 */
	BUSINESS_USERS_PASSWORD_NOT_MIXED_CASE(40020, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * User cannot be created because the repeated password does not match the password.
	 */
	BUSINESS_USERS_PASSWORD_MISMATCH(40021, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	/**
	 * User Password can not be changed because the current password is not correct.
	 */
	BUSINESS_USERS_CURRENT_PASSWORD_INCORRECT(40022, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	//</editor-fold>
	
	/*
	 * Errors indicating that the request is valid but failed because of a server error:
	 * - internal server errors;
	 * - external provider errors.
	 */
	//<editor-fold defaultstate="collapsed" desc="Server Errors (70000-79999)">
	/**
	 * An unexpected server error occurred.
	 */
	SERVER_UNEXPECTED(70000, EApiHttpStatusCodes.INTERNAL_SERVER_ERROR),
	/**
	 * The server failed to find a unique identifier for the object being created. The client is
	 * unlucky and may retry the request.
	 */
	SERVER_KEY_GENERATION_FAILED(70001, EApiHttpStatusCodes.INTERNAL_SERVER_ERROR),
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Access Limitation Errors (80000-89999)">
	/**
	 * Authentication failed because the supplied credentials are invalid.
	 */
	ACCESS_BAD_CREDENTIALS(80000, EApiHttpStatusCodes.UNAUTHORIZED),
	/**
	 * 	Authentication failed because the supplied access token is invalid or malformed.
	 */
	ACCESS_BAD_ACCESS_TOKEN(80001, EApiHttpStatusCodes.UNAUTHORIZED),
	/**
	 * 	Authentication failed because the supplied access token is expired.
	 */
	ACCESS_EXPIRED_TOKEN(80002, EApiHttpStatusCodes.UNAUTHORIZED),
	/**
	 * The request was refused because the client does not have the required permission
	 * to access the requested resource.
	 */
	ACCESS_REQUIRED_PERMISSION_MISSING(80003, EApiHttpStatusCodes.FORBIDDEN);
	//</editor-fold>

	/*
	 * Errors indicating that the request failed because access to the resource is limited:
	 * - quota limitations;
	 * - locked resources.
	 */
	//<editor-fold defaultstate="collapsed" desc="Implementation">
	private int code;
	private EApiHttpStatusCodes defaultHttpStatusCode;

	private EApiErrorCodes(int code, EApiHttpStatusCodes defaultHttpStatusCode) {
		this.code = code;
		this.defaultHttpStatusCode = defaultHttpStatusCode;
	}

	@Override
	public int getDefaultHttpStatusCode() {
		return defaultHttpStatusCode.getCode();
	}

	@Override
	public int getCode() {
		return code;
	}
	//</editor-fold>
}
