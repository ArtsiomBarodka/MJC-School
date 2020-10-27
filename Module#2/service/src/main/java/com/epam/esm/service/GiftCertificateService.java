package com.epam.esm.service;

import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    boolean isAlreadyExist(@NonNull String giftCertificateName);
    @NonNull List<GiftCertificate> getListGiftCertificatesWithTagsByTagName(@NonNull String tagName, @NonNull SortMode sortMode);
    @NonNull List<GiftCertificate> getListGiftCertificatesWithTagsBySearch(@NonNull String key, @NonNull SortMode sortMode);
    @NonNull Optional<GiftCertificate> getGiftCertificatesById(@NonNull Long id);
    @NonNull Long create(@NonNull GiftCertificate giftCertificate);
    @NonNull GiftCertificate update(@NonNull GiftCertificate giftCertificate, @NonNull Long id);
    void delete (@NonNull Long id);
}
