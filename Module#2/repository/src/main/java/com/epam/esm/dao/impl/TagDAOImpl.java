package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

/**
 * The type Tag dao.
 */
@Repository
public class TagDAOImpl implements TagDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    /**
     * Instantiates a new Tag dao.
     *
     * @param dataSource the data source
     */
    @Autowired
    public TagDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("tag")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Tag> findById(Long id) throws RepositoryException {
        String sql = "SELECT tag.*, certificate.* FROM tag " +
                "LEFT JOIN gift_certificate_tag ON tag.id = gift_certificate_tag.tag_id " +
                "LEFT JOIN certificate ON gift_certificate_tag.gift_certificate_id = certificate.id where tag.id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.query(sql, new TagWithGiftCertificatesExtractor(), id));
        } catch (DataAccessException ex) {
            LOGGER.error("Can`t get tag from dao layer.", ex);
            throw new RepositoryException("Can`t get tag from dao layer.", ex);
        }
    }

    @Override
    public boolean isAlreadyExistByName(String tagName) throws RepositoryException {
        String sql = "SELECT count(*) FROM tag WHERE name = ?";

        try {
            int count = jdbcTemplate.queryForObject(sql, new Object[]{tagName}, Integer.class);
            return count > 0;
        } catch (DataAccessException ex) {
            LOGGER.error("Can`t detect if tag exists at dao layer.", ex);
            throw new RepositoryException("Can`t detect if tag exists at dao layer.", ex);
        }
    }

    @Override
    public List<Tag> getListTagsByGiftCertificateId(Long id) throws RepositoryException {
        String sql = "SELECT tag.*, certificate.* FROM tag " +
                "INNER JOIN gift_certificate_tag ON tag.id = gift_certificate_tag.tag_id " +
                "INNER JOIN certificate ON gift_certificate_tag.gift_certificate_id = certificate.id where certificate.id = ?";

        try {
            List<Tag> result = jdbcTemplate.query(sql, new ListTagsWithGiftCertificatesExtractor(), id);
            return Optional.ofNullable(result).orElse(Collections.emptyList());
        } catch (DataAccessException ex) {
            LOGGER.error("Can`t get tags list from dao layer.", ex);
            throw new RepositoryException("Can`t get tags list from dao layer.", ex);
        }
    }

    @Override
    public Long create(Tag tag) throws RepositoryException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", tag.getName());

        try {
            return jdbcInsert.executeAndReturnKey(parameters).longValue();
        } catch (DataAccessException ex) {
            LOGGER.error("Can`t create tag from dao layer.", ex);
            throw new RepositoryException("Can`t create tag from dao layer.", ex);
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        String sql = "DELETE FROM tag WHERE id = ?";

        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException ex) {
            LOGGER.error("Can`t delete tag from dao layer.", ex);
            throw new RepositoryException("Can`t delete tag from dao layer.", ex);
        }
    }
}
