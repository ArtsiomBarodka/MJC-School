package com.epam.esm.model.request;

/**
 * The interface Patch operation.
 *
 * @param <F> the type parameter
 */
@FunctionalInterface
public interface PatchOperation<F> {
    /**
     * Merge to entity.
     *
     * @param entity the entity
     */
    void mergeToEntity(F entity);
}
