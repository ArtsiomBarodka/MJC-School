package com.epam.esm.dao;

import com.epam.esm.domain.Page;
import com.epam.esm.entity.Tag;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDAO {
    @NonNull
    Optional<Tag> findById(@NonNull Long id);

    @NonNull
    Tag save(@NonNull Tag tag);

    void delete(@NonNull Long id);

    public boolean isExistById(@NonNull Long id);

    boolean isExistByName(@NonNull String name);

    @NonNull
    List<Tag> listAllTagsSortByIdAsc(@NonNull Page page);

    @NonNull
    List<Tag> listAllTagsSortByIdDesc(@NonNull Page page);

    @NonNull
    List<Tag> listAllTagsSortByNameAsc(@NonNull Page page);

    @NonNull
    List<Tag> listAllTagsSortByNameDesc(@NonNull Page page);

    @NonNull
    List<Tag> listTagsByGiftCertificateIdSortByIdAsc(@NonNull Long giftCertificateId,
                                                     @NonNull Page page);

    @NonNull
    List<Tag> listTagsByGiftCertificateIdSortByIdDesc(@NonNull Long giftCertificateId,
                                                      @NonNull Page page);

    @NonNull
    List<Tag> listTagsByGiftCertificateIdSortByNameAsc(@NonNull Long giftCertificateId,
                                                       @NonNull Page page);

    @NonNull
    List<Tag> listTagsByGiftCertificateIdSortByNameDesc(@NonNull Long giftCertificateId,
                                                        @NonNull Page page);

    Long allTagsCount();

    Long allTagsByGiftCertificateIdCount(Long giftCertificateId);

}
