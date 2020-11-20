package com.epam.esm.service;

import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface GiftCertificateService {

    @NonNull
    List<GiftCertificate> getAllListGiftCertificatesWithTags(@NonNull Page page, @NonNull SortMode sortMode) throws ResourceNotFoundException;

    @NonNull
    List<GiftCertificate> getListGiftCertificatesWithTagsByTagNames(@NonNull List<String> tagName, @NonNull Page page, @NonNull SortMode sortMode) throws ResourceNotFoundException;

    @NonNull
    GiftCertificate getGiftCertificatesById(@NonNull Long id) throws ResourceNotFoundException;

    @NonNull
    Long create(@NonNull GiftCertificate giftCertificate) throws ResourceAlreadyExistException, BadParametersException;

    @NonNull
    void update(@NonNull GiftCertificate giftCertificate, @NonNull Long id) throws ResourceNotFoundException, BadParametersException;

    @NonNull
    GiftCertificate updateAndReturn(@NonNull GiftCertificate giftCertificate, @NonNull Long id) throws ResourceNotFoundException, BadParametersException, ServiceException;

    void delete(@NonNull Long id) throws ResourceNotFoundException;

    List<GiftCertificate> criteriaListByNames(@NonNull Page page, @NonNull SortMode sortMode, @NonNull List<String> tagNames);
}
