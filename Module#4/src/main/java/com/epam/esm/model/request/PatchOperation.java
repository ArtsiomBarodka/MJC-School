package com.epam.esm.model.request;

@FunctionalInterface
public interface PatchOperation<F> {
    void mergeToEntity(F entity);
}
