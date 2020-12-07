package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    void getAllTest_RESOURCE_IN_NOT_EXIST() {
        Pageable pageableMock = mock(Pageable.class);

        when(giftCertificateDAO.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.getAll(pageableMock));
    }

    @Test
    void getAllTest_SHOULD_RETURN_PAGE_OF_GIFT_CERTIFICATES() throws ResourceNotFoundException {
        Page expected = mock(Page.class);

        Pageable pageableMock = mock(Pageable.class);

        when(expected.hasContent())
                .thenReturn(true);
        when(giftCertificateDAO.findAll(any(Pageable.class)))
                .thenReturn(expected);

        Page<GiftCertificate> actual = giftCertificateService.getAll(pageableMock);

        assertIterableEquals(expected, actual);
    }

    @Test
    void getListByTagNamesTest_RESOURCE_IN_NOT_EXIST() {
        List<String> tagNames = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        Pageable pageableMock = mock(Pageable.class);

        when(tagDAO.getByNameIn(anyList()))
                .thenReturn(tags);

        when(giftCertificateDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.getListByTagNames(tagNames, pageableMock));
    }

    @Test
    void getListByTagNamesTest_SHOULD_RETURN_PAGE_OF_GIFT_CERTIFICATES() throws ResourceNotFoundException {
        Page expected = mock(Page.class);

        List<String> tagNames = new ArrayList<>();
        tagNames.add("tagName");
        List<Tag> tagsMock = new ArrayList<>();
        Pageable pageableMock = mock(Pageable.class);

        when(expected.hasContent())
                .thenReturn(true);

        when(tagDAO.getByNameIn(anyList()))
                .thenReturn(tagsMock);

        when(giftCertificateDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(expected);

        Page<GiftCertificate> actual = giftCertificateService.getListByTagNames(tagNames, pageableMock);

        assertIterableEquals(expected, actual);
    }

    @Test
    void getByIdTest_RESOURCE_IN_NOT_EXIST() {
        Long id = 1L;

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.getById(id));
    }

    @Test
    void getByIdTest_SHOULD_RETURN_GIFT_CERTIFICATE() throws ResourceNotFoundException {
        Long id = 1L;

        GiftCertificate expected = spy(GiftCertificate.class);

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        GiftCertificate actual = giftCertificateService.getById(id);

        assertEquals(expected, actual);
    }

    @Test
    void createTest_GIFT_CERTIFICATE_ALREADY_EXIST_WITH_NAME() {
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateMock.getName()).thenReturn("");

        when(giftCertificateDAO.existsByName(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class,
                () -> giftCertificateService.create(giftCertificateMock));
    }

    @Test
    void createTest_CURRENT_TAG_IS_NOT_EXIST() {
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        Tag tagMock = mock(Tag.class);
        List<Tag> tags = new ArrayList<>();
        tags.add(tagMock);

        when(giftCertificateMock.getName()).thenReturn("");
        when(giftCertificateMock.getTags()).thenReturn(tags);
        when(tagMock.getId()).thenReturn(1L);

        when(giftCertificateDAO.existsByName(anyString()))
                .thenReturn(false);

        when(tagDAO.existsById(any(Long.TYPE)))
                .thenReturn(false);

        assertThrows(BadParametersException.class,
                () -> giftCertificateService.create(giftCertificateMock));
    }

    @Test
    void createTest_SHOULD_CREATE_GIFT_CERTIFICATE()
            throws BadParametersException, ResourceAlreadyExistException {

        Long expected = 1L;

        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        Tag tagMock = mock(Tag.class);
        List<Tag> tags = new ArrayList<>();
        tags.add(tagMock);

        when(giftCertificateMock.getName()).thenReturn("");
        when(giftCertificateMock.getTags()).thenReturn(tags);
        when(giftCertificateMock.getId()).thenReturn(expected);
        when(tagMock.getId()).thenReturn(1L);

        when(giftCertificateDAO.existsByName(anyString()))
                .thenReturn(false);

        when(tagDAO.existsById(any(Long.TYPE)))
                .thenReturn(true);

        when(giftCertificateDAO.save(any(GiftCertificate.class)))
                .thenReturn(giftCertificateMock);

        Long actual = giftCertificateService.create(giftCertificateMock);

        assertEquals(expected, actual);
    }

    @Test
    void updateTest_GIFT_CERTIFICATE_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.update(giftCertificateMock, id));
    }

    @Test
    void updateTest_GIFT_CERTIFICATE_UPDATED_NAME_ALREADY_EXIST() {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        GiftCertificate repositoryGiftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateMock.getName()).thenReturn("name");
        when(repositoryGiftCertificateMock.getName()).thenReturn("anotherName");

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(repositoryGiftCertificateMock));

        when(giftCertificateDAO.existsByName(anyString()))
                .thenReturn(true);

        assertThrows(BadParametersException.class,
                () -> giftCertificateService.update(giftCertificateMock, id));
    }

    @Test
    void updateTest_UDATED_TAG_IS_NOT_EXIST() {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        Tag tagMock = mock(Tag.class);
        List<Tag> tags = new ArrayList<>();
        tags.add(tagMock);

        when(giftCertificateMock.getName()).thenReturn("name");
        when(giftCertificateMock.getTags()).thenReturn(tags);
        when(tagMock.getId()).thenReturn(1L);

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(giftCertificateMock));

        when(giftCertificateDAO.existsByName(anyString()))
                .thenReturn(false);

        when(tagDAO.existsById(any(Long.TYPE)))
                .thenReturn(false);

        assertThrows(BadParametersException.class,
                () -> giftCertificateService.update(giftCertificateMock, id));
    }

    @Test
    void updateTest_SHOULD_UPDATE_GIFT_CERTIFICATE() throws ResourceNotFoundException, BadParametersException {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        Tag tagMock = mock(Tag.class);
        List<Tag> tags = new ArrayList<>();
        tags.add(tagMock);

        when(giftCertificateMock.getName()).thenReturn("name");
        when(giftCertificateMock.getTags()).thenReturn(tags);
        when(tagMock.getId()).thenReturn(1L);

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(giftCertificateMock));

        when(giftCertificateDAO.existsByName(anyString()))
                .thenReturn(false);

        when(tagDAO.existsById(any(Long.TYPE)))
                .thenReturn(true);

        GiftCertificate actual = giftCertificateService.update(giftCertificateMock, id);

        assertNotNull(actual);
    }

    @Test
    void deleteTest_GIFT_CERTIFICATE_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.delete(id));
    }

}