package com.epam.esm.rest.controller.annotation;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The interface With mock admin.
 */
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(roles="ADMIN")
public @interface WithMockAdmin {
}
