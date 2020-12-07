package com.epam.esm.model.patch;

@FunctionalInterface
public interface PatchOperation<F> {
    void mergeToEntity(F entity);
}
