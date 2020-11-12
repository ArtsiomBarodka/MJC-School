package com.epam.esm.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Pageable;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagDAOImpl implements TagDAO {
    private static final String ALL_TAGS_QUERY_SORT_BY_ID_ASC = "SELECT t FROM Tag t ORDER BY t.id ASC";
    private static final String ALL_TAGS_QUERY_SORT_BY_ID_DESC = "SELECT t FROM Tag t ORDER BY t.id DESC";
    private static final String ALL_TAGS_QUERY_SORT_BY_NAME_ASC = "SELECT t FROM Tag t ORDER BY t.name ASC";
    private static final String ALL_TAGS_QUERY_SORT_BY_NAME_DESC = "SELECT t FROM Tag t ORDER BY t.name DESC";

    private static final String ALL_TAGS_COUNT = "SELECT count (t.id) FROM Tag t";

    private static final String ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_ID_ASC = "SELECT t FROM Tag t JOIN t.giftCertificates c WHERE c.id = :cid ORDER BY t.id ASC";
    private static final String ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_ID_DESC = "SELECT t FROM Tag t JOIN t.giftCertificates c WHERE c.id = :cid ORDER BY t.id DESC";
    private static final String ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_NAME_ASC = "SELECT t FROM Tag t JOIN t.giftCertificates c WHERE c.id = :cid ORDER BY t.name ASC";
    private static final String ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_NAME_DESC = "SELECT t FROM Tag t JOIN t.giftCertificates c WHERE c.id = :cid ORDER BY t.name DESC";

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
    public List<Tag> listAllTagsSortByIdAsc(Pageable pageable) {
        return getListTags(ALL_TAGS_QUERY_SORT_BY_ID_ASC, pageable, null);
    }

    @Override
    public List<Tag> listAllTagsSortByIdDesc(Pageable pageable) {
        return getListTags(ALL_TAGS_QUERY_SORT_BY_ID_DESC, pageable, null);
    }

    @Override
    public List<Tag> listAllTagsSortByNameAsc(Pageable pageable) {
        return getListTags(ALL_TAGS_QUERY_SORT_BY_NAME_ASC, pageable, null);
    }

    @Override
    public List<Tag> listAllTagsSortByNameDesc(Pageable pageable) {
        return getListTags(ALL_TAGS_QUERY_SORT_BY_NAME_DESC, pageable, null);
    }

    @Override
    public List<Tag> listTagsByGiftCertificateIdSortByIdAsc(Long giftCertificateId, Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", giftCertificateId);
        return getListTags(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_ID_ASC, pageable, params);
    }

    @Override
    public List<Tag> listTagsByGiftCertificateIdSortByIdDesc(Long giftCertificateId, Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", giftCertificateId);
        return getListTags(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_ID_DESC, pageable, params);
    }

    @Override
    public List<Tag> listTagsByGiftCertificateIdSortByNameAsc(Long giftCertificateId, Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", giftCertificateId);
        return getListTags(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_NAME_ASC, pageable, params);
    }

    @Override
    public List<Tag> listTagsByGiftCertificateIdSortByNameDesc(Long giftCertificateId, Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", giftCertificateId);
        return getListTags(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_NAME_DESC, pageable, params);
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

    private List<Tag> getListTags(String sql, Pageable pageable, Map<String, Object> params) {
        TypedQuery<Tag> query = entityManager.createQuery(sql, Tag.class);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getSize());
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.getResultList();
    }


}
