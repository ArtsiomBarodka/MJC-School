package com.epam.esm.patch;

@FunctionalInterface
public interface PatchOperation<F> {
    void mergeToEntity(F entity);
}
