package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

public interface TagDAO {
    Optional<Tag> findById(long id);
    @NonNull List<Tag> getListTagsByGiftCertificateId(long id);
    long create(@NonNull Tag tag);
    void delete(long id);
}
