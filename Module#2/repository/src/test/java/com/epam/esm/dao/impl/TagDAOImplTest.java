package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DataConfiguration;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = DataConfiguration.class)
public class TagDAOImplTest {

}
