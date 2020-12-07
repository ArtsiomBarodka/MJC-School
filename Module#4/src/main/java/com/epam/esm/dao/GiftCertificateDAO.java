package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

public interface GiftCertificateDAO extends PagingAndSortingRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {
    boolean existsByName(@NonNull String name);
}


