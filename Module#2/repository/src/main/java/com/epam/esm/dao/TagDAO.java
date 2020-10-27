package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

public interface TagDAO {
    Optional<Tag> findById(@NonNull Long id);
    boolean isAlreadyExistByName(@NonNull String  tagName);
    @NonNull List<Tag> getListTagsByGiftCertificateId(@NonNull Long id);
    @NonNull Long create(@NonNull Tag tag);
    void delete(@NonNull Long id);
}
