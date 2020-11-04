package com.epam.esm.dao;

import com.epam.esm.exception.repository.RepositoryException;
import org.springframework.lang.NonNull;

/**
 * The interface Gift certificate tag dao.
 */
public interface GiftCertificateTagDAO {
    /**
     * Save gift certificate tag.
     *
     * @param giftCertificateId the gift certificate id
     * @param tagId             the tag id
     * @throws RepositoryException the repository exception
     */
    void saveGiftCertificateTag(@NonNull Long giftCertificateId, @NonNull Long tagId) throws RepositoryException;

    /**
     * Delete gift certificate tag.
     *
     * @param giftCertificateId the gift certificate id
     * @param tagId             the tag id
     * @throws RepositoryException the repository exception
     */
    void deleteGiftCertificateTag(@NonNull Long giftCertificateId, @NonNull Long tagId) throws RepositoryException;
}
