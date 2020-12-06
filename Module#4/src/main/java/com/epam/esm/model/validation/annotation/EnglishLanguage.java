package com.epam.esm.model.validation.annotation;

import com.epam.esm.model.validation.validator.EnglishLanguageConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The interface English language.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnglishLanguageConstraintValidator.class)
public @interface EnglishLanguage {

    /**
     * Message string.
     *
     * @return the string
     */
    String message() default "Value must have only English characters";

    /**
     * With numbers boolean.
     *
     * @return the boolean
     */
// 0123456789
    boolean withNumbers() default true;

    /**
     * With punctuations boolean.
     *
     * @return the boolean
     */
//.,?!-:()'"[]{}; \t\n
    boolean withPunctuations() default true;

    /**
     * With spec symbols boolean.
     *
     * @return the boolean
     */
//~#$%^&*-+=_\\|/@`!'\";:><,.?{}
    boolean withSpecSymbols() default true;

    /**
     * Payload class [ ].
     *
     * @return the class [ ]
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Groups class [ ].
     *
     * @return the class [ ]
     */
    Class<?>[] groups() default {};
}
