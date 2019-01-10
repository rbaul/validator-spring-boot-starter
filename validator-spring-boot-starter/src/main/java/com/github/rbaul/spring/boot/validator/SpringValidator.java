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
     */
    @AliasFor("validators")
    Class<? extends Validator>[] value() default {};

    /**
     * Validators (default: Empty)
     */
    @AliasFor("value")
    Class<? extends Validator>[] validators() default {};

    /**
     * Groups of validators (default: Empty)
     */
    Class<?>[] groups() default {};

    /**
     * Validate constraints before execution validators (default: False)
     */
    boolean validateConstraintBefore() default false;

    /**
     * First not valid validator throw (default: False)
     */
    boolean firstValidatorThrow() default false;

    /**
     * First not valid group throw (default: False)
     */
    boolean firstGroupThrow() default false;
}
