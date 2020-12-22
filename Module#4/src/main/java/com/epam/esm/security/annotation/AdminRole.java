package com.epam.esm.security.annotation;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Admin role.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Secured("ROLE_ADMIN")
public @interface AdminRole {

}
