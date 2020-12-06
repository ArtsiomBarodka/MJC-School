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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * The type Tag service impl test.
 */
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagDAO tagDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @InjectMocks
    private TagServiceImpl tagService;

    /**
     * Create test tag already exist with name.
     */
    @Test
    void createTest_TAG_ALREADY_EXIST_WITH_NAME() {
        Tag tagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("");

        when(tagDAO.isExistByName(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class,
                () -> tagService.create(tagMock));
    }

    /**
     * Create test should create tag.
     *
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @Test
    void createTest_SHOULD_CREATE_TAG() throws ResourceAlreadyExistException, BadParametersException {
        Long expected = 1L;

        Tag tagMock = mock(Tag.class);
        when(tagMock.getId()).thenReturn(expected);
        when(tagMock.getName()).thenReturn("");

        when(tagDAO.isExistByName(anyString()))
                .thenReturn(false);

        when(tagDAO.save(any(Tag.class))).thenReturn(tagMock);

        Long actual = tagService.create(tagMock);

        assertEquals(expected, actual);
    }

    /**
     * Gets the most widely used tag of user from the highest cost of all orders test tag is not exist with user id.
     */
    @Test
    void getTheMostWidelyUsedTagOfUserFromTheHighestCostOfAllOrdersTest_TAG_IS_NOT_EXIST_WITH_USER_ID() {
        Long id = 1L;

        when(tagDAO.findTheMostWidelyUsedOfUserWithTheHighestCostOfAllOrders(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.getTheMostWidelyUsedTagOfUserFromTheHighestCostOfAllOrders(id));
    }

    /**
     * Gets the most widely used tag of user from the highest cost of all orders test should return tag.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getTheMostWidelyUsedTagOfUserFromTheHighestCostOfAllOrdersTest_SHOULD_RETURN_TAG()
            throws ResourceNotFoundException {

        Tag expected = mock(Tag.class);

        Long id = 1L;

        when(tagDAO.findTheMostWidelyUsedOfUserWithTheHighestCostOfAllOrders(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        Tag actual = tagService.getTheMostWidelyUsedTagOfUserFromTheHighestCostOfAllOrders(id);

        assertEquals(expected, actual);
    }

    /**
     * Delete test tag is not exist with id.
     */
    @Test
    void deleteTest_TAG_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.delete(id));
    }

    /**
     * Gets by id test resource in not exist.
     */
    @Test
    void getByIdTest_RESOURCE_IN_NOT_EXIST() {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.getById(id));
    }

    /**
     * Gets by id test should return tag.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getByIdTest_SHOULD_RETURN_TAG() throws ResourceNotFoundException {
        Tag expected = mock(Tag.class);

        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        Tag actual = tagService.getById(id);

        assertEquals(expected, actual);
    }

    /**
     * Update test tag is not exist with id.
     */
    @Test
    void updateTest_TAG_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;
        Tag tagMock = mock(Tag.class);

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.update(tagMock, id));
    }

    /**
     * Update test tag updated name already exist.
     */
    @Test
    void updateTest_TAG_UPDATED_NAME_ALREADY_EXIST() {
        Long id = 1L;
        Tag tagMock = mock(Tag.class);
        Tag repositoryTagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("name");
        when(repositoryTagMock.getName()).thenReturn("anotherName");

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(repositoryTagMock));

        when(tagDAO.isExistByName(anyString()))
                .thenReturn(true);

        assertThrows(BadParametersException.class,
                () -> tagService.update(tagMock, id));
    }

    /**
     * Update test udated gift certificate is not exist.
     */
    @Test
    void updateTest_UDATED_GIFT_CERTIFICATE_IS_NOT_EXIST() {
        Long id = 1L;
        Tag tagMock = mock(Tag.class);
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificateMock);


        when(tagMock.getName()).thenReturn("name");
        when(tagMock.getGiftCertificates()).thenReturn(giftCertificates);
        when(giftCertificateMock.getId()).thenReturn(1L);

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(tagMock));

        when(tagDAO.isExistByName(anyString()))
                .thenReturn(false);

        when(giftCertificateDAO.isExistById(any(Long.TYPE)))
                .thenReturn(false);

        assertThrows(BadParametersException.class,
                () -> tagService.update(tagMock, id));
    }

    /**
     * Update test should update tag.
     *
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @Test
    void updateTest_SHOULD_UPDATE_TAG() throws ResourceNotFoundException, BadParametersException {
        Long id = 1L;
        Tag tagMock = mock(Tag.class);
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificateMock);


        when(tagMock.getName()).thenReturn("name");
        when(tagMock.getGiftCertificates()).thenReturn(giftCertificates);
        when(giftCertificateMock.getId()).thenReturn(1L);

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(tagMock));

        when(tagDAO.isExistByName(anyString()))
                .thenReturn(false);

        when(giftCertificateDAO.isExistById(any(Long.TYPE)))
                .thenReturn(true);

        Tag actual = tagService.update(tagMock, id);

        assertNotNull(actual);
    }

    /**
     * Gets all test resource in not exist.
     */
    @Test
    void getAllTest_RESOURCE_IN_NOT_EXIST() {
        Page page = new Page();
        SortMode sortMode = SortMode.ID_ASC;

        when(tagDAO.listAll(any(Page.class), any(SortMode.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.getAll(page, sortMode));
    }

    /**
     * Gets all test should return list.
     *
     * @param sortMode the sort mode
     * @throws ResourceNotFoundException the resource not found exception
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class, names = {"ID_ASC", "ID_DESC", "NAME_ASC", "NAME_DESC"})
    void getAllTest_SHOULD_RETURN_LIST(SortMode sortMode) throws ResourceNotFoundException {
        List<Tag> expected = new ArrayList<>();
        expected.add(spy(Tag.class));

        Page page = new Page();

        when(tagDAO.listAll(any(Page.class), any(SortMode.class)))
                .thenReturn(expected);

        List<Tag> actual = tagService.getAll(page, sortMode);

        assertIterableEquals(expected, actual);
    }

    /**
     * Gets list by gift certificate id test resource in not exist.
     */
    @Test
    void getListByGiftCertificateIdTest_RESOURCE_IN_NOT_EXIST() {
        Long giftCertificateId = 1L;
        Page page = new Page();
        SortMode sortMode = SortMode.ID_ASC;

        when(tagDAO.listByGiftCertificateId(anyLong(), any(Page.class), any(SortMode.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.getListByGiftCertificateId(giftCertificateId, page, sortMode));
    }

    /**
     * Gets list by gift certificate id test should return list.
     *
     * @param sortMode the sort mode
     * @throws ResourceNotFoundException the resource not found exception
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class, names = {"ID_ASC", "ID_DESC", "NAME_ASC", "NAME_DESC"})
    void getListByGiftCertificateIdTest_SHOULD_RETURN_LIST(SortMode sortMode) throws ResourceNotFoundException {
        List<Tag> expected = new ArrayList<>();
        expected.add(spy(Tag.class));

        Long giftCertificateId = 1L;
        Page page = new Page();

        when(tagDAO.listByGiftCertificateId(anyLong(), any(Page.class), any(SortMode.class)))
                .thenReturn(expected);

        List<Tag> actual = tagService.getListByGiftCertificateId(giftCertificateId, page, sortMode);

        assertIterableEquals(expected, actual);
    }
}
