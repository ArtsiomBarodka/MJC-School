package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository("GiftCertificateDAO")
public class JDBCGiftCertificatesDAO implements GiftCertificateDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JDBCGiftCertificatesDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        String sql = "SELECT g.*FROM gift_сertificate AS g WHERE g.id = ?";

        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(sql, new Object[]{id}, new GiftCertificateMapper()));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        String sql = "UPDATE gift_сertificate  AS g " +
                "SET g.name = :name, g.description = :description, g.price = :price, g.duration = :duration " +
                "WHERE g.id = :id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", giftCertificate.getId());
        source.addValue("name", giftCertificate.getName());
        source.addValue("description", giftCertificate.getDescription());
        source.addValue("price", giftCertificate.getPrice());
        source.addValue("duration", giftCertificate.getDuration());

        namedParameterJdbcTemplate.update(sql, source);

    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        String sql = "INSERT INTO gift_сertificate (name, description, price, duration) " +
                "VALUES (:name, :description, :price, :duration)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", giftCertificate.getName());
        source.addValue("description", giftCertificate.getDescription());
        source.addValue("price", giftCertificate.getPrice());
        source.addValue("duration", giftCertificate.getDuration());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        return namedParameterJdbcTemplate.update(sql, source, keyHolder, new String[]{"id"});
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM gift_сertificate WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateAsc(String tagName) {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where t.name = ? ORDER BY g.create_date ASC";

        return jdbcTemplate.query(sql, new GiftCertificateWithTagsExtractor(), tagName);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateDesc(String tagName) {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where t.name = ? ORDER BY g.create_date DESC";

        return jdbcTemplate.query(sql, new GiftCertificateWithTagsExtractor(), tagName);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameAsc(String tagName) {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where t.name = ? ORDER BY g.name ASC";

        return jdbcTemplate.query(sql, new GiftCertificateWithTagsExtractor(), tagName);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameDesc(String tagName) {
        String sql = "SELECT g.*, t.* FROM gift_сertificate AS g " +
                "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                "INNER JOIN tag AS t ON gct.tag_id = t.id where t.name = ? ORDER BY g.name DESC";

        return jdbcTemplate.query(sql, new GiftCertificateWithTagsExtractor(), tagName);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(String key) {
        String sql = "CALL searchGiftCertificatesSearchSortByDateAsc(?)";

        return jdbcTemplate.query(sql, new GiftCertificateWithTagsExtractor(), key);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(String key) {
        String sql = "CALL searchGiftCertificatesSearchSortByDateDesc(?)";

        return jdbcTemplate.query(sql, new GiftCertificateWithTagsExtractor(), key);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(String key) {
        String sql = "CALL searchGiftCertificatesSearchSortByNameAsc(?)";

        return jdbcTemplate.query(sql, new GiftCertificateWithTagsExtractor(), key);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(String key) {
        String sql = "CALL searchGiftCertificatesSearchSortByNameDesc(?)";

        return jdbcTemplate.query(sql, new GiftCertificateWithTagsExtractor(), key);
    }

}
