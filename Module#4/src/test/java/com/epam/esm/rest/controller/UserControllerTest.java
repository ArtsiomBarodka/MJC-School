package com.epam.esm.rest.controller;

import com.epam.esm.rest.controller.annotation.WithMockAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User controller test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private static final String GET_ALL_USERS_URI = "/api/v1/users";
    private static final String GET_USER_BY_ID_URI = "/api/v1/users/1";

    private static final String PAGE_SIZE_PARAMETER_NAME = "size";
    private static final String PAGE_SIZE_PARAMETER_VALUE = "1";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Gets all users test with user role should return status ok.
     *
     * @throws Exception the exception
     */
    @WithMockUser
    @Test
    void getAllUsersTest_WITH_USER_ROLE_SHOULD_RETURN_STATUS_OK() throws Exception {
        mvc.perform(get(GET_ALL_USERS_URI)
                .param(PAGE_SIZE_PARAMETER_NAME, PAGE_SIZE_PARAMETER_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Gets all users test with admin role should return status ok.
     *
     * @throws Exception the exception
     */
    @WithMockAdmin
    @Test
    void getAllUsersTest_WITH_ADMIN_ROLE_SHOULD_RETURN_STATUS_OK() throws Exception {
        mvc.perform(get(GET_ALL_USERS_URI)
                .param(PAGE_SIZE_PARAMETER_NAME, PAGE_SIZE_PARAMETER_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Gets all users test without role should return status unauthorized.
     *
     * @throws Exception the exception
     */
    @Test
    void getAllUsersTest_WITHOUT_ROLE_SHOULD_RETURN_STATUS_UNAUTHORIZED() throws Exception {
        mvc.perform(get(GET_ALL_USERS_URI)
                .param(PAGE_SIZE_PARAMETER_NAME, PAGE_SIZE_PARAMETER_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Gets by id test with admin role should return status ok.
     *
     * @throws Exception the exception
     */
    @WithMockAdmin
    @Test
    void  getByIdTest_WITH_ADMIN_ROLE_SHOULD_RETURN_STATUS_OK() throws Exception {
        mvc.perform(get(GET_USER_BY_ID_URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Gets by id test without role should return status unauthorized.
     *
     * @throws Exception the exception
     */
    @Test
    void getByIdTest_WITHOUT_ROLE_SHOULD_RETURN_STATUS_UNAUTHORIZED() throws Exception {
        mvc.perform(get(GET_USER_BY_ID_URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
