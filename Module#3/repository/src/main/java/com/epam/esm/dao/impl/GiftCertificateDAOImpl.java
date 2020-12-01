package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Gift certificate dao.
 */
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
        if (giftCertificate.getId() != null) {
            return entityManager.merge(giftCertificate);
        }
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
    public List<GiftCertificate> listAll(Page page, SortMode sortMode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificate = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(giftCertificate);

        SortMode.Entry split = SortMode.split(sortMode);
        criteriaQuery.orderBy(split.getDestination().equalsIgnoreCase("asc") ?
                criteriaBuilder.asc(giftCertificate.get(split.getField())) :
                criteriaBuilder.desc(giftCertificate.get(split.getField())));

        TypedQuery<GiftCertificate> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getSize());
        return query.getResultList();
    }

    @Override
    public List<GiftCertificate> listByTagNames(List<String> tagNames, Page page, SortMode sortMode) {
        String select = "SELECT c";
        Map<String, Object> params = new HashMap<>();
        tagNames.forEach(name -> params.put(name, name));
        return getListGiftCertificates(buildSqlForAllGiftCertificatesByTagNames(select, params, sortMode), page, params);
    }

    @Override
    public Long countAll() {
        Query query = entityManager.createQuery(ALL_CERTIFICATES_COUNT);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long countByTagNames(List<String> tagNames) {
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

    private String buildSqlForAllGiftCertificatesByTagNames(String select, Map<String, Object> tagNames, SortMode sortMode) {
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
        if (sortMode != null) {
            SortMode.Entry split = SortMode.split(sortMode);
            result.append(" ORDER BY ")
                    .append(split.getField())
                    .append(" ")
                    .append(split.getDestination());
        }

        return result.toString();
    }
}
