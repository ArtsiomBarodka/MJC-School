package com.epam.esm.dao;

import com.epam.esm.exception.repository.RepositoryException;
import org.springframework.lang.NonNull;

public interface GiftCertificateTagDAO {
    void saveGiftCertificateTag(@NonNull Long giftCertificateId, @NonNull Long tagId) throws RepositoryException;

    void deleteGiftCertificateTag(@NonNull Long giftCertificateId, @NonNull Long tagId) throws RepositoryException;
}
