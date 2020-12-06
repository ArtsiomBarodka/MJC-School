package com.epam.esm.dao.impl;

import com.epam.esm.model.domain.Page;
import com.epam.esm.model.domain.SortMode;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
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
import java.util.Optional;

/**
 * The type Tag dao.
 */
@Repository
public class TagDAOImpl implements com.epam.esm.dao.TagDAO {
    private static final String ALL_TAGS_COUNT = "SELECT count (t.id) FROM Tag t";
    private static final String ALL_TAGS_BY_GIFT_CERTIFICATE_ID_COUNT = "SELECT count (t.id) FROM Tag t JOIN t.giftCertificates c WHERE c.id = :cid";
    private static final String FIND_TAG_BY_NAME = "SELECT count (t.id) FROM Tag t WHERE t.name = :tname";
    private static final String FIND_THE_MOST_WIDELY_USED_TAG_QUERY = "select tag.* from tag " +
            "inner join gift_certificate_tag on gift_certificate_tag.tag_id=tag.id " +
            "inner join certificate on certificate.id=gift_certificate_tag.gift_certificate_id " +
            "inner join certificate_order on certificate_order.certificate_id=certificate.id " +
            "inner join user_order on user_order.id = certificate_order.order_id " +
            "inner join user on user.id = user_order.fk_user_id " +
            "where user.id=?1 " +
            "group by tag.id, user_order.id " +
            "order by user_order.price desc ,count(tag.id) desc " +
            "limit 1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findTheMostWidelyUsedOfUserWithTheHighestCostOfAllOrders(Long userId) {
        Query query = entityManager.createNativeQuery(FIND_THE_MOST_WIDELY_USED_TAG_QUERY, Tag.class);
        query.setParameter(1, userId);
        List<Tag> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(resultList.get(0));
    }

    @Override
    public Tag save(Tag tag) {
        if (tag.getId() != null) {
            return entityManager.merge(tag);
        }
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
    public List<Tag> listAll(Page page, SortMode sortMode) {
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
    public List<Tag> listByGiftCertificateId(Long giftCertificateId, Page page, SortMode sortMode) {
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

    public Long countAll() {
        Query query = entityManager.createQuery(ALL_TAGS_COUNT);
        return (Long) query.getSingleResult();
    }

    public Long countByGiftCertificateId(Long giftCertificateId) {
        Query query = entityManager.createQuery(ALL_TAGS_BY_GIFT_CERTIFICATE_ID_COUNT);
        query.setParameter("cid", giftCertificateId);
        return (Long) query.getSingleResult();
    }


}
