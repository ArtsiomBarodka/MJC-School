package com.epam.esm.dao;

import org.springframework.lang.NonNull;

public interface GiftCertificateTagDAO {
    void saveGiftCertificateTag(@NonNull Long giftCertificateId, @NonNull Long tagId);
    void deleteGiftCertificateTag(@NonNull Long giftCertificateId, @NonNull Long tagId);
}
