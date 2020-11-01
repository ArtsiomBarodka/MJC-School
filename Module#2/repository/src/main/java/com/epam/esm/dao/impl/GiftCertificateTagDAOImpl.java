package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.exception.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("GiftCertificateTagDAO")
public class GiftCertificateTagDAOImpl implements GiftCertificateTagDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiftCertificateTagDAOImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GiftCertificateTagDAOImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void saveGiftCertificateTag(Long giftCertificateId, Long tagId) throws RepositoryException {
        String sql = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) " +
                "VALUES (:gift_certificate_id, :tag_id)";

        try {
            namedParameterJdbcTemplate.update(sql, getMapSqlParameterSource(giftCertificateId, tagId));
        } catch (DataAccessException ex){
            LOGGER.error("Can`t save dependence between gift certificate and tag to dao layer.", ex);
            throw new RepositoryException("Can`t save dependence between gift certificate and tag to dao layer.",ex);
        }
    }

    @Override
    public void deleteGiftCertificateTag(Long giftCertificateId, Long tagId) throws RepositoryException {
        String sql = "DELETE FROM gift_certificate_tag " +
                "WHERE gift_certificate_id = :gift_certificate_id AND tag_id = :tag_id";

        try {
            namedParameterJdbcTemplate.update(sql, getMapSqlParameterSource(giftCertificateId, tagId));
        } catch (DataAccessException ex){
            LOGGER.error("Can`t delete dependence between gift certificate and tag from dao layer.", ex);
            throw new RepositoryException("Can`t delete dependence between gift certificate and tag from dao layer.",ex);
        }
    }

    private MapSqlParameterSource getMapSqlParameterSource(long giftCertificateId, long tagId) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("gift_certificate_id", giftCertificateId);
        source.addValue("tag_id", tagId);
        return source;
    }
}
