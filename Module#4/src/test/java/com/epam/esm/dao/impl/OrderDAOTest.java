package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Sql({"classpath:dump/insert.sql"})
@DataJpaTest
class OrderDAOTest {

    @Autowired
    private OrderDAO orderDAO;

    @Test
    void getOrdersByUserIdTest_SHOULD_RETURN_NOT_EMPTY_PAGE_OF_ORDERS() {
        Long existingUserId = 1L;

        assertThat(orderDAO.getOrdersByUserId(existingUserId, Pageable.unpaged())).isNotEmpty();
    }

    @Test
    void getOrdersByUserIdTest_SHOULD_RETURN_EMPTY_RESULT() {
        Long notExistingUserId = 20L;

        assertThat(orderDAO.getOrdersByUserId(notExistingUserId, Pageable.unpaged())).isEmpty();
    }
}
