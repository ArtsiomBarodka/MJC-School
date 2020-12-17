package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

public interface GiftCertificateService {

    @NonNull
    Page<GiftCertificate> getAll(@NonNull Pageable pageable)
            throws ResourceNotFoundException;

    @NonNull
    Page<GiftCertificate> getListByTagNames(@NonNull List<String> names, @NonNull Pageable pageable)
            throws ResourceNotFoundException;

    @NonNull
    GiftCertificate getById(@NonNull Long id)
            throws ResourceNotFoundException;

    @NonNull
    Long create(@NonNull GiftCertificate giftCertificate)
            throws ResourceAlreadyExistException, BadParametersException;

    @NonNull
    GiftCertificate update(@NonNull GiftCertificate giftCertificate, @NonNull Long id)
            throws ResourceNotFoundException, BadParametersException;

    void delete(@NonNull Long id)
            throws ResourceNotFoundException;
}
