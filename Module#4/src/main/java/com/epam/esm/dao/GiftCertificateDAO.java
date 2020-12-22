package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDAO extends PagingAndSortingRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {
    /**
     * Exists by name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(@NonNull String name);
}


