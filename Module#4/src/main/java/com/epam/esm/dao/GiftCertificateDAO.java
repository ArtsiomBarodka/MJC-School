package com.epam.esm.dao;

import com.epam.esm.model.domain.Page;
import com.epam.esm.model.domain.SortMode;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDAO {
    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    @NonNull
    Optional<GiftCertificate> findById(@NonNull Long id);

    /**
     * Save gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    @NonNull
    GiftCertificate save(@NonNull GiftCertificate giftCertificate);

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
    List<GiftCertificate> listAll(@NonNull Page page, @NonNull SortMode sortMode);

    /**
     * List by tag names list.
     *
     * @param tagNames the tag names
     * @param page     the page
     * @param sortMode the sort mode
     * @return the list
     */
    @NonNull
    List<GiftCertificate> listByTagNames(@NonNull List<String> tagNames, @NonNull Page page, @NonNull SortMode sortMode);

    /**
     * Count all long.
     *
     * @return the long
     */
    @NonNull
    Long countAll();

    /**
     * Count by tag names long.
     *
     * @param tagNames the tag names
     * @return the long
     */
    @NonNull
    Long countByTagNames(@NonNull List<String> tagNames);
}


