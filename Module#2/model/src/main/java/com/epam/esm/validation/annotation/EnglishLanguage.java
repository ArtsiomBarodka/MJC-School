package com.epam.esm.validation.annotation;

import com.epam.esm.validation.validator.EnglishLanguageConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnglishLanguageConstraintValidator.class)
public @interface EnglishLanguage {

    String message() default "Value must have only English characters";

    // 0123456789
    boolean withNumbers() default true;

    //.,?!-:()'"[]{}; \t\n
    boolean withPunctuations() default true;

    //~#$%^&*-+=_\\|/@`!'\";:><,.?{}
    boolean withSpecSymbols() default true;

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
