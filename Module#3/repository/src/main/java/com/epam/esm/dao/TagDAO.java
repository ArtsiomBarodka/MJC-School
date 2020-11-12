package com.epam.esm.dao;

import com.epam.esm.domain.Pageable;
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
    List<Tag> listAllTagsSortByIdAsc(@NonNull Pageable pageable);

    @NonNull
    List<Tag> listAllTagsSortByIdDesc(@NonNull Pageable pageable);

    @NonNull
    List<Tag> listAllTagsSortByNameAsc(@NonNull Pageable pageable);

    @NonNull
    List<Tag> listAllTagsSortByNameDesc(@NonNull Pageable pageable);

    @NonNull
    List<Tag> listTagsByGiftCertificateIdSortByIdAsc(@NonNull Long giftCertificateId,
                                                     @NonNull Pageable pageable);

    @NonNull
    List<Tag> listTagsByGiftCertificateIdSortByIdDesc(@NonNull Long giftCertificateId,
                                                      @NonNull Pageable pageable);

    @NonNull
    List<Tag> listTagsByGiftCertificateIdSortByNameAsc(@NonNull Long giftCertificateId,
                                                       @NonNull Pageable pageable);

    @NonNull
    List<Tag> listTagsByGiftCertificateIdSortByNameDesc(@NonNull Long giftCertificateId,
                                                        @NonNull Pageable pageable);

    Long allTagsCount();

    Long allTagsByGiftCertificateIdCount(Long giftCertificateId);

}
