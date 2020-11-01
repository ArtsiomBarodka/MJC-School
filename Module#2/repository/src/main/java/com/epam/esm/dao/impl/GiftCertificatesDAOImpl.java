package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository("GiftCertificateDAO")
public class GiftCertificatesDAOImpl implements GiftCertificateDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiftCertificatesDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GiftCertificatesDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) throws RepositoryException {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where g.id = ? ";

        try {
            return Optional.ofNullable(jdbcTemplate
                    .query(sql, new GiftCertificateWithTagsExtractor(), id));

        } catch (DataAccessException ex){
            LOGGER.error("Can`t get gift certificate from dao layer.", ex);
            throw new RepositoryException("Can`t get gift certificate from dao layer.",ex);
        }
    }

    @Override
    public boolean isAlreadyExistByName(String giftCertificateName) throws RepositoryException {
        String sql = "SELECT count(*) FROM gift_сertificate WHERE name = ?";

        try {
            int count = jdbcTemplate.queryForObject(sql, new Object[]{"giftCertificateName"}, Integer.class);
            return count > 0;
        } catch (DataAccessException ex){
            LOGGER.error("Can`t detect if gift certificate exists at dao level.", ex);
            throw new RepositoryException("Can`t detect if gift certificate exists at dao level.",ex);
        }
    }


    @Override
    public void update(GiftCertificate giftCertificate) throws RepositoryException {
        String sql = "UPDATE gift_сertificate  AS g " +
                "SET g.name = :name, g.description = :description, g.price = :price, g.duration = :duration " +
                "WHERE g.id = :id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", giftCertificate.getId());
        source.addValue("name", giftCertificate.getName());
        source.addValue("description", giftCertificate.getDescription());
        source.addValue("price", giftCertificate.getPrice());
        source.addValue("duration", giftCertificate.getDuration());

        try{
            namedParameterJdbcTemplate.update(sql, source);
        } catch (DataAccessException ex){
            LOGGER.error("Can`t update gift certificate from dao level.", ex);
            throw new RepositoryException("Can`t update gift certificate from dao level.",ex);
        }
    }

    @Override
    public Long create(GiftCertificate giftCertificate) throws RepositoryException {
        String sql = "INSERT INTO gift_сertificate (name, description, price, duration) " +
                "VALUES (:name, :description, :price, :duration)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", giftCertificate.getName());
        source.addValue("description", giftCertificate.getDescription());
        source.addValue("price", giftCertificate.getPrice());
        source.addValue("duration", giftCertificate.getDuration());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try{
            return (long) namedParameterJdbcTemplate.update(sql, source, keyHolder, new String[]{"id"});
        } catch (DataAccessException ex){
            LOGGER.error("Can`t create gift certificate from dao level.", ex);
            throw new RepositoryException("Can`t create gift certificate from dao level.",ex);
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        String sql = "DELETE FROM gift_сertificate WHERE id = ?";

        try{
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException ex){
            LOGGER.error("Can`t delete gift certificate from dao level.", ex);
            throw new RepositoryException("Can`t delete gift certificate from dao level.",ex);
        }
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateAsc(String tagName) throws RepositoryException {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where t.name = ? ORDER BY g.create_date ASC";

        return getListGiftCertificatesWithOneParameter(sql, tagName);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateDesc(String tagName) throws RepositoryException {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where t.name = ? ORDER BY g.create_date DESC";

        return getListGiftCertificatesWithOneParameter(sql, tagName);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameAsc(String tagName) throws RepositoryException {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where t.name = ? ORDER BY g.name ASC";

        return getListGiftCertificatesWithOneParameter(sql, tagName);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameDesc(String tagName) throws RepositoryException {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where t.name = ? ORDER BY g.name DESC";

        return getListGiftCertificatesWithOneParameter(sql, tagName);
    }


    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(String key) throws RepositoryException {
        String sql = "CALL searchGiftCertificatesSearchSortByDateAsc(?)";

        return getListGiftCertificatesWithOneParameter(sql, key);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(String key) throws RepositoryException {
        String sql = "CALL searchGiftCertificatesSearchSortByDateDesc(?)";

        return getListGiftCertificatesWithOneParameter(sql, key);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(String key) throws RepositoryException {
        String sql = "CALL searchGiftCertificatesSearchSortByNameAsc(?)";

        return getListGiftCertificatesWithOneParameter(sql, key);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(String key) throws RepositoryException {
        String sql = "CALL searchGiftCertificatesSearchSortByNameDesc(?)";

        return getListGiftCertificatesWithOneParameter(sql, key);
    }

    private List<GiftCertificate> getListGiftCertificatesWithOneParameter(String sql, String parameter) throws RepositoryException {
        try {
            List<GiftCertificate> result = jdbcTemplate.query(sql, new ListGiftCertificatesWithTagsExtractor(), parameter);
            return Optional.ofNullable(result).orElse(Collections.emptyList());
        } catch (DataAccessException ex){
            LOGGER.error("Can`t get gift certificates list from dao level.", ex);
            throw new RepositoryException("Can`t get gift certificates list from dao level.",ex);
        }
    }
}
