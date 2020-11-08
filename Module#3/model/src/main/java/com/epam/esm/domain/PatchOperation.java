package com.epam.esm.domain;

@FunctionalInterface
public interface PatchOperation<F> {
    void mergeToEntity(F entity);
}
