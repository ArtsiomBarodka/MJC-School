package com.epam.esm.dao;

import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDAO {
    @NonNull
    Optional<GiftCertificate> findById(@NonNull Long id);

    @NonNull
    GiftCertificate save(@NonNull GiftCertificate giftCertificate);

    void delete(@NonNull Long id);

    boolean isExistById(@NonNull Long id);

    boolean isExistByName(@NonNull String name);

    @NonNull
    List<GiftCertificate> listAll(@NonNull Page page, @NonNull SortMode sortMode);

    @NonNull
    List<GiftCertificate> listByTagNames(@NonNull List<String> tagNames, @NonNull Page page, @NonNull SortMode sortMode);

    @NonNull
    Long countAll();

    @NonNull
    Long countByTagNames(@NonNull List<String> tagNames);
}


