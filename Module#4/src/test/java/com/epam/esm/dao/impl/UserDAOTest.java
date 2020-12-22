package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type User dao test.
 */
@ActiveProfiles("test")
@Sql({"classpath:dump/insert.sql"})
@DataJpaTest
class UserDAOTest {
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_FIRST_NAME = "firstName";
    private static final String USER_LAST_NAME = "lastName";

    @Autowired
    private UserDAO userDAO;

    private final User user;

    /**
     * Instantiates a new User dao test.
     */
    public UserDAOTest() {
        user = new User();
        user.setUsername(USER_USERNAME);
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
    }

    /**
     * Delete user in db.
     */
    @AfterEach
    void deleteUserInDb() {
        if (userDAO.existsById(user.getId())) {
            userDAO.deleteById(user.getId());
        }
    }

    /**
     * Create user in db.
     */
    @BeforeEach
    void createUserInDb() {
        userDAO.save(user);
    }

    /**
     * Exists by username test should return true.
     */
    @Test
    void existsByUsernameTest_SHOULD_RETURN_TRUE() {
        assertThat(userDAO.existsByUsername(user.getUsername())).isTrue();
    }

    /**
     * Exists by username test should return false.
     */
    @Test
    void existsByUsernameTest_SHOULD_RETURN_FALSE() {
        assertThat(userDAO.existsByUsername(" ")).isFalse();
    }


    /**
     * Find by username test should return optional of user.
     */
    @Test
    void findByUsernameTest_SHOULD_RETURN_OPTIONAL_OF_USER() {
        assertThat(userDAO.findByUsername(user.getUsername())).contains(user);
    }

    /**
     * Find by username test should return empty optional.
     */
    @Test
    void findByUsernameTest_SHOULD_RETURN_EMPTY_OPTIONAL() {
        assertThat(userDAO.findByUsername("")).isEmpty();
    }
}
