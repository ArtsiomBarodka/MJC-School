package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
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

@Repository("TagDAO")
public class JDBCTagDAO implements TagDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JDBCTagDAO(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Tag> findById(long id) {
        String sql = "SELECT t.* FROM tag AS t WHERE t.id = ?";

        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(sql, new Object[]{id}, new TagMapper()));
        } catch (IncorrectResultSizeDataAccessException ex){
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> getListTagsByGiftCertificateId(long id) {
        String sql = "SELECT t.*, g.* FROM tag AS t " +
                "INNER JOIN gift_certificate_tag AS gct ON t.id = gct.tag_id " +
                "INNER JOIN gift_сertificate AS g ON gct.tag_id = g.id where g.id = ?";

        return jdbcTemplate.query(sql, new TagWithGiftCertificatesExtractor(), id);
    }

    @Override
    public long create(Tag tag) {
        String sql = "INSERT INTO tag (name) VALUES (:name)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", tag.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        return namedParameterJdbcTemplate.update(sql, source, keyHolder, new String[]{"id"});
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM tag WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
