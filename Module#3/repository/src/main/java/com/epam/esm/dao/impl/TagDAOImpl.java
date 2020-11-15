package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Page;
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
    public List<Tag> listAllTagsSortByIdAsc(Page page) {
        return getListTags(ALL_TAGS_QUERY_SORT_BY_ID_ASC, page, null);
    }

    @Override
    public List<Tag> listAllTagsSortByIdDesc(Page page) {
        return getListTags(ALL_TAGS_QUERY_SORT_BY_ID_DESC, page, null);
    }

    @Override
    public List<Tag> listAllTagsSortByNameAsc(Page page) {
        return getListTags(ALL_TAGS_QUERY_SORT_BY_NAME_ASC, page, null);
    }

    @Override
    public List<Tag> listAllTagsSortByNameDesc(Page page) {
        return getListTags(ALL_TAGS_QUERY_SORT_BY_NAME_DESC, page, null);
    }

    @Override
    public List<Tag> listTagsByGiftCertificateIdSortByIdAsc(Long giftCertificateId, Page page) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", giftCertificateId);
        return getListTags(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_ID_ASC, page, params);
    }

    @Override
    public List<Tag> listTagsByGiftCertificateIdSortByIdDesc(Long giftCertificateId, Page page) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", giftCertificateId);
        return getListTags(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_ID_DESC, page, params);
    }

    @Override
    public List<Tag> listTagsByGiftCertificateIdSortByNameAsc(Long giftCertificateId, Page page) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", giftCertificateId);
        return getListTags(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_NAME_ASC, page, params);
    }

    @Override
    public List<Tag> listTagsByGiftCertificateIdSortByNameDesc(Long giftCertificateId, Page page) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", giftCertificateId);
        return getListTags(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_QUERY_SORT_BY_NAME_DESC, page, params);
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
