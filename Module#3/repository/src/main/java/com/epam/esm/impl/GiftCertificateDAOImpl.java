package com.epam.esm.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.Pageable;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private static final String FIND_CERTIFICATE_BY_NAME = "SELECT count (c.id) FROM GiftCertificate c WHERE c.name = :cname";

    private static final String ALL_CERTIFICATES_QUERY_SORT_BY_ID_ASC = "SELECT c FROM GiftCertificate c ORDER BY c.id ASC";
    private static final String ALL_CERTIFICATES_QUERY_SORT_BY_ID_DESC = "SELECT c FROM GiftCertificate c ORDER BY c.id DESC";
    private static final String ALL_CERTIFICATES_QUERY_SORT_BY_NAME_ASC = "SELECT c FROM GiftCertificate c ORDER BY c.name ASC";
    private static final String ALL_CERTIFICATES_QUERY_SORT_BY_NAME_DESC = "SELECT c FROM GiftCertificate c ORDER BY c.name DESC";
    private static final String ALL_CERTIFICATES_QUERY_SORT_BY_DATE_ASC = "SELECT c FROM GiftCertificate c ORDER BY c.createDate ASC";
    private static final String ALL_CERTIFICATES_QUERY_SORT_BY_DATE_DESC = "SELECT c FROM GiftCertificate c ORDER BY c.createDate DESC";

    private static final String ALL_CERTIFICATES_COUNT = "SELECT count (c.id) FROM GiftCertificate c";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void delete(Long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(giftCertificate);
    }

    @Override
    public boolean isExistById(Long id) {
        return entityManager.find(GiftCertificate.class, id) != null;
    }

    @Override
    public boolean isExistByName(String name) {
        Query query = entityManager.createQuery(FIND_CERTIFICATE_BY_NAME);
        query.setParameter("cname", name);
        return (long)query.getSingleResult() > 0;
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesSortByIdAsc(Pageable pageable) {
        return getListGiftCertificates(ALL_CERTIFICATES_QUERY_SORT_BY_ID_ASC, pageable, null);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesSortByIdDesc(Pageable pageable) {
        return getListGiftCertificates(ALL_CERTIFICATES_QUERY_SORT_BY_ID_DESC, pageable, null);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesSortByNameAsc(Pageable pageable) {
        return getListGiftCertificates(ALL_CERTIFICATES_QUERY_SORT_BY_NAME_ASC, pageable, null);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesSortByNameDesc(Pageable pageable) {
        return getListGiftCertificates(ALL_CERTIFICATES_QUERY_SORT_BY_NAME_DESC, pageable, null);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesSortByDateAsc(Pageable pageable) {
        return getListGiftCertificates(ALL_CERTIFICATES_QUERY_SORT_BY_DATE_ASC, pageable, null);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesSortByDateDesc(Pageable pageable) {
        return getListGiftCertificates(ALL_CERTIFICATES_QUERY_SORT_BY_DATE_DESC, pageable, null);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByIdAsc(List<String> tagNames, Pageable pageable) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.id ASC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), pageable, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByIdDesc(List<String> tagNames, Pageable pageable) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.id DESC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), pageable, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByNameAsc(List<String> tagNames, Pageable pageable) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.name ASC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), pageable, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByNameDesc(List<String> tagNames, Pageable pageable) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.name DESC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), pageable, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByDateAsc(List<String> tagNames, Pageable pageable) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.createDate ASC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), pageable, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByDateDesc(List<String> tagNames, Pageable pageable) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.createDate DESC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), pageable, params);
    }

    @Override
    public Long allGiftCertificatesCount() {
        Query query = entityManager.createQuery(ALL_CERTIFICATES_COUNT);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long allGiftCertificatesByTagNamesCount(List<String> tagNames) {
        String select = "SELECT count (c.id)";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        Query query = entityManager.createQuery(buildSqlForAllGiftCertificatesByTagNames(select, params, null));
        params.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private List<GiftCertificate> getListGiftCertificates(String sql, Pageable pageable, Map<String, Object> params) {
        Query query = entityManager.createQuery(sql);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getSize());
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.getResultList();
    }

    private String buildSqlForAllGiftCertificatesByTagNames(String select, Map<String, Object> tagNames, String orderBy) {
        StringBuilder result = new StringBuilder(select);
        result.append(" FROM GiftCertificate c ");
        if (!tagNames.isEmpty()) {
            StringBuilder where = new StringBuilder("WHERE ");
            tagNames.forEach((k, v) -> where.append("(SELECT t1 FROM Tag t1 WHERE t1.name = :")
                    .append(v)
                    .append(")")
                    .append(" MEMBER OF c.tags AND "));
            result.append(where.substring(0, where.lastIndexOf("AND")));
        }
        if (orderBy != null) {
            result.append(orderBy);
        }
        return result.toString();
    }

}
