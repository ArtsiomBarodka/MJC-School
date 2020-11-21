package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagDAOImpl implements TagDAO {
    private static final String ALL_TAGS_COUNT = "SELECT count (t.id) FROM Tag t";
    private static final String ALL_TAGS_BY_GIFT_CERTIFICATE_ID_COUNT = "SELECT count (t.id) FROM Tag t JOIN t.giftCertificates c WHERE c.id = :cid";
    private static final String FIND_TAG_BY_NAME = "SELECT count (t.id) FROM Tag t WHERE t.name = :tname";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag save(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }

    @Override
    public boolean isExistById(Long id) {
        return entityManager.find(Tag.class, id) != null;
    }

    @Override
    public boolean isExistByName(String name) {
        Query query = entityManager.createQuery(FIND_TAG_BY_NAME);
        query.setParameter("tname", name);
        return (long) query.getSingleResult() > 0;
    }

    @Override
    public List<Tag> listAllTags(Page page, SortMode sortMode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tag = criteriaQuery.from(Tag.class);

        criteriaQuery.select(tag);

        SortMode.Entry split = SortMode.split(sortMode);
        criteriaQuery.orderBy(split.getDestination().equalsIgnoreCase("asc") ?
                criteriaBuilder.asc(tag.get(split.getField())) :
                criteriaBuilder.desc(tag.get(split.getField())));

        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getSize());
        return query.getResultList();
    }


    @Override
    public List<Tag> listTagsByGiftCertificateId(Long giftCertificateId, Page page, SortMode sortMode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tag = criteriaQuery.from(Tag.class);
        Join<Tag, GiftCertificate> giftCertificate = tag.join("giftCertificates");

        criteriaQuery.select(tag).where(criteriaBuilder.equal(giftCertificate.get("id"), giftCertificateId));

        SortMode.Entry split = SortMode.split(sortMode);
        criteriaQuery.orderBy(split.getDestination().equalsIgnoreCase("asc") ?
                criteriaBuilder.asc(tag.get(split.getField())) :
                criteriaBuilder.desc(tag.get(split.getField())));

        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getSize());
        return query.getResultList();

    }

    public Long allTagsCount() {
        Query query = entityManager.createQuery(ALL_TAGS_COUNT);
        return (Long) query.getSingleResult();
    }

    public Long allTagsByGiftCertificateIdCount(Long giftCertificateId) {
        Query query = entityManager.createQuery(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_COUNT);
        query.setParameter("cid", giftCertificateId);
        return (Long) query.getSingleResult();
    }

    private List<Tag> getListTags(String sql, Page page, Map<String, Object> params) {
        TypedQuery<Tag> query = entityManager.createQuery(sql, Tag.class);
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getSize());
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.getResultList();
    }


}
