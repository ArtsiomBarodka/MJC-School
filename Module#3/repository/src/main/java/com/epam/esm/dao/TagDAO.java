package com.epam.esm.dao;

import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.Tag;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDAO {
    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    @NonNull
    Optional<Tag> findById(@NonNull Long id);

    /**
     * Find the most widely used of user with the highest cost of all orders optional.
     *
     * @param userId the user id
     * @return the optional
     */
    @NonNull
    Optional<Tag> findTheMostWidelyUsedOfUserWithTheHighestCostOfAllOrders(@NonNull Long userId);

    /**
     * Save tag.
     *
     * @param tag the tag
     * @return the tag
     */
    @NonNull
    Tag save(@NonNull Tag tag);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(@NonNull Long id);

    /**
     * Is exist by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean isExistById(@NonNull Long id);

    /**
     * Is exist by name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean isExistByName(@NonNull String name);

    /**
     * List all list.
     *
     * @param page     the page
     * @param sortMode the sort mode
     * @return the list
     */
    @NonNull
    List<Tag> listAll(@NonNull Page page, @NonNull SortMode sortMode);

    /**
     * List by gift certificate id list.
     *
     * @param giftCertificateId the gift certificate id
     * @param page              the page
     * @param sortMode          the sort mode
     * @return the list
     */
    @NonNull
    List<Tag> listByGiftCertificateId(@NonNull Long giftCertificateId, @NonNull Page page, @NonNull SortMode sortMode);

    /**
     * Count all long.
     *
     * @return the long
     */
    @NonNull
    Long countAll();

    /**
     * Count by gift certificate id long.
     *
     * @param giftCertificateId the gift certificate id
     * @return the long
     */
    @NonNull
    Long countByGiftCertificateId(@NonNull Long giftCertificateId);

}
