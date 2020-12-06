package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.domain.Page;
import com.epam.esm.model.domain.SortMode;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

/**
 * The type Gift certificate service impl test.
 */
@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    /**
     * Gets all test resource in not exist.
     */
    @Test
    void getAllTest_RESOURCE_IN_NOT_EXIST() {
        Page page = new Page();
        SortMode sortMode = SortMode.ID_ASC;

        when(giftCertificateDAO.listAll(any(Page.class), any(SortMode.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.getAll(page, sortMode));
    }

    /**
     * Gets all test should return list.
     *
     * @param sortMode the sort mode
     * @throws ResourceNotFoundException the resource not found exception
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void getAllTest_SHOULD_RETURN_LIST(SortMode sortMode) throws ResourceNotFoundException {
        Page page = new Page();

        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(spy(GiftCertificate.class));

        when(giftCertificateDAO.listAll(any(Page.class), any(SortMode.class)))
                .thenReturn(expected);

        List<GiftCertificate> actual = giftCertificateService.getAll(page, sortMode);

        assertIterableEquals(expected, actual);
    }

    /**
     * Gets list by tag names test resource in not exist.
     */
    @Test
    void getListByTagNamesTest_RESOURCE_IN_NOT_EXIST() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("tagName");
        Page page = new Page();
        SortMode sortMode = SortMode.ID_ASC;

        when(giftCertificateDAO.listByTagNames(anyList(), any(Page.class), any(SortMode.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.getListByTagNames(tagNames, page, sortMode));
    }

    /**
     * Gets list by tag names test should return list.
     *
     * @param sortMode the sort mode
     * @throws ResourceNotFoundException the resource not found exception
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void getListByTagNamesTest_SHOULD_RETURN_LIST(SortMode sortMode) throws ResourceNotFoundException {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("tagName");
        Page page = new Page();

        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(spy(GiftCertificate.class));

        when(giftCertificateDAO.listByTagNames(anyList(), any(Page.class), any(SortMode.class)))
                .thenReturn(expected);

        List<GiftCertificate> actual = giftCertificateService.getListByTagNames(tagNames, page, sortMode);

        assertIterableEquals(expected, actual);
    }

    /**
     * Gets by id test resource in not exist.
     */
    @Test
    void getByIdTest_RESOURCE_IN_NOT_EXIST() {
        Long id = 1L;

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.getById(id));
    }

    /**
     * Gets by id test should return gift certificate.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getByIdTest_SHOULD_RETURN_GIFT_CERTIFICATE() throws ResourceNotFoundException {
        Long id = 1L;

        GiftCertificate expected = spy(GiftCertificate.class);

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        GiftCertificate actual = giftCertificateService.getById(id);

        assertEquals(expected, actual);
    }

    /**
     * Create test gift certificate already exist with name.
     */
    @Test
    void createTest_GIFT_CERTIFICATE_ALREADY_EXIST_WITH_NAME() {
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateMock.getName()).thenReturn("");

        when(giftCertificateDAO.isExistByName(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class,
                () -> giftCertificateService.create(giftCertificateMock));
    }

    /**
     * Create test current tag is not exist.
     */
    @Test
    void createTest_CURRENT_TAG_IS_NOT_EXIST() {
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        Tag tagMock = mock(Tag.class);
        List<Tag> tags = new ArrayList<>();
        tags.add(tagMock);

        when(giftCertificateMock.getName()).thenReturn("");
        when(giftCertificateMock.getTags()).thenReturn(tags);
        when(tagMock.getId()).thenReturn(1L);

        when(giftCertificateDAO.isExistByName(anyString()))
                .thenReturn(false);

        when(tagDAO.isExistById(any(Long.TYPE)))
                .thenReturn(false);

        assertThrows(BadParametersException.class,
                () -> giftCertificateService.create(giftCertificateMock));
    }

    /**
     * Create test should create gift certificate.
     *
     * @throws BadParametersException        the bad parameters exception
     * @throws ResourceAlreadyExistException the resource already exist exception
     */
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

        when(giftCertificateDAO.isExistByName(anyString()))
                .thenReturn(false);

        when(tagDAO.isExistById(any(Long.TYPE)))
                .thenReturn(true);

        when(giftCertificateDAO.save(any(GiftCertificate.class)))
                .thenReturn(giftCertificateMock);

        Long actual = giftCertificateService.create(giftCertificateMock);

        assertEquals(expected, actual);
    }

    /**
     * Update test gift certificate is not exist with id.
     */
    @Test
    void updateTest_GIFT_CERTIFICATE_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.update(giftCertificateMock, id));
    }

    /**
     * Update test gift certificate updated name already exist.
     */
    @Test
    void updateTest_GIFT_CERTIFICATE_UPDATED_NAME_ALREADY_EXIST() {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        GiftCertificate repositoryGiftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateMock.getName()).thenReturn("name");
        when(repositoryGiftCertificateMock.getName()).thenReturn("anotherName");

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(repositoryGiftCertificateMock));

        when(giftCertificateDAO.isExistByName(anyString()))
                .thenReturn(true);

        assertThrows(BadParametersException.class,
                () -> giftCertificateService.update(giftCertificateMock, id));
    }

    /**
     * Update test udated tag is not exist.
     */
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

        when(giftCertificateDAO.isExistByName(anyString()))
                .thenReturn(false);

        when(tagDAO.isExistById(any(Long.TYPE)))
                .thenReturn(false);

        assertThrows(BadParametersException.class,
                () -> giftCertificateService.update(giftCertificateMock, id));
    }

    /**
     * Update test should update gift certificate.
     *
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
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

        when(giftCertificateDAO.isExistByName(anyString()))
                .thenReturn(false);

        when(tagDAO.isExistById(any(Long.TYPE)))
                .thenReturn(true);

        GiftCertificate actual = giftCertificateService.update(giftCertificateMock, id);

        assertNotNull(actual);
    }

    /**
     * Delete test gift certificate is not exist with id.
     */
    @Test
    void deleteTest_GIFT_CERTIFICATE_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.delete(id));
    }

}