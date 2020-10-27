package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository("TagDAO")
public class TagDAOImpl implements TagDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TagDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        String sql = "SELECT t.*, g.* FROM tag AS t " +
                "INNER JOIN gift_certificate_tag AS gct ON t.id = gct.tag_id " +
                "INNER JOIN gift_сertificate AS g ON gct.tag_id = g.id where t.id = ?";

        return Optional.ofNullable(jdbcTemplate.query(sql, new TagWithGiftCertificatesExtractor(), id));
    }

    @Override
    public boolean isAlreadyExistByName(String tagName) {
        String sql = "SELECT count(*) FROM tag WHERE name = ?";

        int count = jdbcTemplate.queryForObject(sql, new Object[]{"tagName"}, Integer.class);
        return count > 0;
    }

    @Override
    public List<Tag> getListTagsByGiftCertificateId(Long id) {
        String sql = "SELECT t.*, g.* FROM tag AS t " +
                "INNER JOIN gift_certificate_tag AS gct ON t.id = gct.tag_id " +
                "INNER JOIN gift_сertificate AS g ON gct.tag_id = g.id where g.id = ?";

        return jdbcTemplate.query(sql, new ListTagsWithGiftCertificatesExtractor(), id);
    }

    @Override
    public Long create(Tag tag) {
        String sql = "INSERT INTO tag (name) VALUES (:name)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", tag.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        return (long) namedParameterJdbcTemplate.update(sql, source, keyHolder, new String[]{"id"});
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM tag WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
