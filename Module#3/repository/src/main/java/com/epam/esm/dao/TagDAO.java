package com.epam.esm.dao;

import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
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
    List<Tag> listAllTags(@NonNull Page page, @NonNull SortMode sortMode);

    @NonNull
    List<Tag> listTagsByGiftCertificateId(@NonNull Long giftCertificateId,
                                          @NonNull Page page, SortMode sortMode);

    Long allTagsCount();

    Long allTagsByGiftCertificateIdCount(Long giftCertificateId);

}
