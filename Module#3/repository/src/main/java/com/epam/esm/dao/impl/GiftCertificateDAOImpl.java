package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private static final String FIND_CERTIFICATE_BY_NAME = "SELECT count (c.id) FROM GiftCertificate c WHERE c.name = :cname";

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
    public List<GiftCertificate> listAllGiftCertificates(Page page, SortMode sortMode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificate = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(giftCertificate);

        String[] s = sortMode.name().split("_");
        criteriaQuery.orderBy(s[1].equalsIgnoreCase("asc") ?
                criteriaBuilder.asc(giftCertificate.get(s[0].toLowerCase())) :
                criteriaBuilder.desc(giftCertificate.get(s[0].toLowerCase())));

        TypedQuery<GiftCertificate> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getSize());
        return query.getResultList();
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByIdAsc(List<String> tagNames, Page page) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.id ASC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), page, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByIdDesc(List<String> tagNames, Page page) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.id DESC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), page, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByNameAsc(List<String> tagNames, Page page) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.name ASC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), page, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByNameDesc(List<String> tagNames, Page page) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.name DESC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), page, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByDateAsc(List<String> tagNames, Page page) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.createDate ASC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), page, params);
    }

    @Override
    public List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByDateDesc(List<String> tagNames, Page page) {
        String select = "SELECT c";
        String orderBy = "ORDER BY c.createDate DESC";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, orderBy), page, params);
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

    private List<GiftCertificate> getListGiftCertificates(String sql, Page page, Map<String, Object> params) {
        Query query = entityManager.createQuery(sql);
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getSize());
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

    public List<GiftCertificate> criteriaListByNames(Page page, SortMode sortMode, List<String> tagNames) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificate = query.from(GiftCertificate.class);
//        Join<GiftCertificate, Tag> tag = giftCertificate.join("tags");

        Subquery<Tag> subquery = query.subquery(Tag.class);
        Root<Tag> from = subquery.from(Tag.class);

        List<Predicate> predicates = new ArrayList<>();
        tagNames.forEach((name) -> {
            predicates.add(criteriaBuilder.equal(from.get("name"), name));
        });
        Predicate[] predicates1 = new Predicate[predicates.size()];

        predicates.toArray(predicates1);
        subquery.select(from).where(criteriaBuilder.or(predicates1));

        query.select(giftCertificate).where(criteriaBuilder.exists(subquery));

//        query.select(giftCertificate).where(criteriaBuilder.in(tag).value(subquery));

        String[] s = sortMode.name().split("_");

        query.orderBy(s[1].equalsIgnoreCase("asc") ? criteriaBuilder.asc(giftCertificate.get(s[0].toLowerCase())) : criteriaBuilder.desc(giftCertificate.get(s[0].toLowerCase())));

        TypedQuery<GiftCertificate> query1 = entityManager.createQuery(query);
        query1.setFirstResult(page.getOffset());
        query1.setMaxResults(page.getSize());
        return query1.getResultList();
    }


}
