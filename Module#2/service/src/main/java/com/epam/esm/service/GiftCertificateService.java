package com.epam.esm.service;

import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface GiftCertificateService {
    @NonNull
    List<GiftCertificate> getAllListGiftCertificatesWithTags(@NonNull SortMode sortMode) throws ServiceException, ResourceNotFoundException;

    @NonNull
    List<GiftCertificate> getListGiftCertificatesWithTagsByTagName(@NonNull String tagName, @NonNull SortMode sortMode) throws ServiceException, ResourceNotFoundException;

    @NonNull
    List<GiftCertificate> getListGiftCertificatesWithTagsBySearch(@NonNull String key, @NonNull SortMode sortMode) throws ServiceException, ResourceNotFoundException;

    @NonNull
    GiftCertificate getGiftCertificatesById(@NonNull Long id) throws ResourceNotFoundException, ServiceException;

    @NonNull
    Long create(@NonNull GiftCertificate giftCertificate) throws ServiceException, ResourceAlreadyExistException;

    @NonNull
    GiftCertificate update(@NonNull GiftCertificate giftCertificate, @NonNull Long id) throws ServiceException, ResourceNotFoundException;

    void delete(@NonNull Long id) throws ServiceException, ResourceNotFoundException;
}
