package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagDAO tagDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void createTest_TAG_ALREADY_EXIST_WITH_NAME() {
        Tag tagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("");

        when(tagDAO.existsByName(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class,
                () -> tagService.create(tagMock));
    }

    @Test
    void createTest_SHOULD_CREATE_TAG() throws ResourceAlreadyExistException, BadParametersException {
        Long expected = 1L;

        Tag tagMock = mock(Tag.class);
        when(tagMock.getId()).thenReturn(expected);
        when(tagMock.getName()).thenReturn("");

        when(tagDAO.existsByName(anyString()))
                .thenReturn(false);

        when(tagDAO.save(any(Tag.class))).thenReturn(tagMock);

        Long actual = tagService.create(tagMock);

        assertEquals(expected, actual);
    }

    @Test
    void getTheMostWidelyUsedTagOfUserFromTheHighestCostOfAllOrdersTest_TAG_IS_NOT_EXIST() {
        when(tagDAO.findTheMostWidelyUsedOfUsersWithTheHighestCostOfAllOrders())
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.getTheMostWidelyUsedTagOfUsersFromTheHighestCostOfAllOrders());
    }

    @Test
    void getTheMostWidelyUsedTagOfUserFromTheHighestCostOfAllOrdersTest_SHOULD_RETURN_TAG()
            throws ResourceNotFoundException {

        Tag expected = mock(Tag.class);

        when(tagDAO.findTheMostWidelyUsedOfUsersWithTheHighestCostOfAllOrders())
                .thenReturn(Optional.of(expected));

        Tag actual = tagService.getTheMostWidelyUsedTagOfUsersFromTheHighestCostOfAllOrders();

        assertEquals(expected, actual);
    }

    @Test
    void deleteTest_TAG_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.delete(id));
    }

    @Test
    void getByIdTest_RESOURCE_IN_NOT_EXIST() {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.getById(id));
    }

    @Test
    void getByIdTest_SHOULD_RETURN_TAG() throws ResourceNotFoundException {
        Tag expected = mock(Tag.class);

        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        Tag actual = tagService.getById(id);

        assertEquals(expected, actual);
    }

    @Test
    void updateTest_TAG_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;
        Tag tagMock = mock(Tag.class);

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.update(tagMock, id));
    }

    @Test
    void updateTest_TAG_UPDATED_NAME_ALREADY_EXIST() {
        Long id = 1L;
        Tag tagMock = mock(Tag.class);
        Tag repositoryTagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("name");
        when(repositoryTagMock.getName()).thenReturn("anotherName");

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(repositoryTagMock));

        when(tagDAO.existsByName(anyString()))
                .thenReturn(true);

        assertThrows(BadParametersException.class,
                () -> tagService.update(tagMock, id));
    }

    @Test
    void updateTest_SHOULD_UPDATE_TAG() throws ResourceNotFoundException, BadParametersException {
        Long id = 1L;
        Tag tagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("name");

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(tagMock));

        when(tagDAO.existsByName(anyString()))
                .thenReturn(false);

        Tag actual = tagService.update(tagMock, id);

        assertNotNull(actual);
    }

    @Test
    void getAllTest_RESOURCE_IN_NOT_EXIST() {
        Pageable pageableMock = mock(Pageable.class);

        when(tagDAO.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.getAll(pageableMock));
    }

    @Test
    void getAllTest_SHOULD_RETURN_PAGE_OF_TAGS() throws ResourceNotFoundException {
        Page expected = mock(Page.class);

        Pageable pageableMock = mock(Pageable.class);

        when(expected.hasContent())
                .thenReturn(true);

        when(tagDAO.findAll(any(Pageable.class)))
                .thenReturn(expected);

        Page<Tag> actual = tagService.getAll(pageableMock);

        assertIterableEquals(expected, actual);
    }

    @Test
    void getListByGiftCertificateIdTest_RESOURCE_IN_NOT_EXIST() {
        Long giftCertificateId = 1L;
        Pageable pageableMock = mock(Pageable.class);

        when(tagDAO.getByGiftCertificates_Id(anyLong(), any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.getListByGiftCertificateId(giftCertificateId, pageableMock));
    }

    @Test
    void getListByGiftCertificateIdTest_SHOULD_RETURN_PAGE_OF_TAGS() throws ResourceNotFoundException {
        Page expected = mock(Page.class);

        Long giftCertificateId = 1L;
        Pageable pageableMock = mock(Pageable.class);

        when(expected.hasContent())
                .thenReturn(true);

        when(tagDAO.getByGiftCertificates_Id(anyLong(), any(Pageable.class)))
                .thenReturn(expected);

        Page<Tag> actual = tagService.getListByGiftCertificateId(giftCertificateId, pageableMock);

        assertIterableEquals(expected, actual);
    }
}
