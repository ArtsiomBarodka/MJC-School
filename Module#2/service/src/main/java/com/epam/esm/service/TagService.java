package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface TagService {
    boolean isAlreadyExist(@NonNull String tagName);
    @NonNull Long create(@NonNull Tag tag);
    void delete(@NonNull Long id);
    @NonNull Optional<Tag> getTagById(@NonNull Long id);
}
