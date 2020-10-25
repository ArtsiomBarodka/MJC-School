package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface EditGiftCertificateAndTagService {
    boolean deleteGiftCertificateById(long id);
    boolean deleteTagById(long id);
    @Nullable GiftCertificate saveGiftCertificate(@NonNull GiftCertificate giftCertificate);
    @Nullable Tag saveTag(@NonNull Tag tag);
}
