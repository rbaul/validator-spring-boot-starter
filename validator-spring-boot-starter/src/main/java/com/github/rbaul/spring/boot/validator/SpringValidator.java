package com.github.rbaul.spring.boot.validator;

import org.springframework.core.annotation.AliasFor;
import org.springframework.validation.Validator;

import java.lang.annotation.*;

/**
 * Marks a method parameter for validation.
 *
 * @author Roman Baul
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpringValidator {

    /**
     * Validators (default: Empty)
     * @return Validator array
     */
    @AliasFor("validators")
    Class<? extends Validator>[] value() default {};

    /**
     * Validators (default: Empty)
     * @return Validator array
     */
    @AliasFor("value")
    Class<? extends Validator>[] validators() default {};

    /**
     * Groups of validators (default: Empty)
     * @return aggregation interface of validators arrays
     */
    Class<?>[] groups() default {};

    /**
     * Validate constraints before execution validators (default: False)
     * @return if validate constraints before
     */
    boolean validateConstraintBefore() default false;

    /**
     * First not valid validator throw (default: False)
     * @return if throw after first not valid validator
     */
    boolean firstValidatorThrow() default false;

    /**
     * First not valid group throw (default: False)
     * @return if throw after first not valid group
     */
    boolean firstGroupThrow() default false;
}
