package com.lotaris.todo.rest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Assigns a code to a validation constraint. If the constraint is violated, the code is sent along
 * with the English error message to allow translation.
 *
 * <p><pre>
 * @Constraint(validatedBy = CheckNotNullValidator.class)
 * @JsonConstraintConverter(code = EConstraintCodes.NOT_NULL)
 * public @interface CheckNotNull {
 * }
 * </pre></p>
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonConstraintConverter {

	EApiErrorLocationType locationType() default EApiErrorLocationType.JSON;

	EApiErrorCodes code();
}
