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

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Sql({"classpath:dump/insert.sql"})
@DataJpaTest
class UserDAOTest {
    private static final String USER_NAME = "name";

    @Autowired
    private UserDAO userDAO;

    private final User user;

    public UserDAOTest() {
        user = new User();
        user.setName(USER_NAME);
    }

    @AfterEach
    void deleteUserInDb() {
        if (userDAO.existsById(user.getId())) {
            userDAO.deleteById(user.getId());
        }
    }

    @BeforeEach
    void createUserInDb() {
        userDAO.save(user);
    }

    @Test
    void existsByNameTest_SHOULD_RETURN_TRUE() {
        assertThat(userDAO.existsByName(user.getName())).isTrue();
    }

    @Test
    void existsByNameTest_SHOULD_RETURN_FALSE() {
        assertThat(userDAO.existsByName(" ")).isFalse();
    }
}
