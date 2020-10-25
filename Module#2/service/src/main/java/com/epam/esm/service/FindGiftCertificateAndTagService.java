package com.epam.esm.service;

import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface FindGiftCertificateAndTagService {
    @Nullable List<GiftCertificate> getListGiftCertificatesWithTagsByTagName(@NonNull String tagName , @NonNull SortMode sortMode);
    @Nullable List<GiftCertificate> getListGiftCertificatesWithTagsSearchByGiftCertificateNameOrDescription(@NonNull String query , @NonNull SortMode sortMode);
    @Nullable GiftCertificate getGiftCertificateById(long id);

    @Nullable List<Tag> getListTagsWithGiftCertificatesByGiftCertificateId(long id);
    @Nullable Tag getTagById(long id);
}
