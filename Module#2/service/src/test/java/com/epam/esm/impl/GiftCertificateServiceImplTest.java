package com.epam.esm.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.repository.RepositoryException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    void getAllListGiftCertificatesWithTagsTest_RESOURCE_IN_NOT_EXIST() throws RepositoryException {
        SortMode sortMode = SortMode.NAME_ASC;

        when(giftCertificateDAO.getAllListGiftCertificatesSortByNameAsc())
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.getAllListGiftCertificatesWithTags(sortMode);
        });
    }

    @Test
    void getAllListGiftCertificatesWithTagsTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        SortMode sortMode = SortMode.NAME_ASC;

        when(giftCertificateDAO.getAllListGiftCertificatesSortByNameAsc())
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            giftCertificateService.getAllListGiftCertificatesWithTags(sortMode);
        });
    }

    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void getAllListGiftCertificatesWithTagsTest_SHOULD_RETURN_LIST(SortMode sortMode) throws RepositoryException, ResourceNotFoundException, ServiceException {
        String tagName = "tagName";
        List<GiftCertificate> expected = List.of(new GiftCertificate());

        when(giftCertificateDAO.getAllListGiftCertificatesSortByNameAsc())
                .thenReturn(expected);
        when(giftCertificateDAO.getAllListGiftCertificatesSortByNameDesc())
                .thenReturn(expected);
        when(giftCertificateDAO.getAllListGiftCertificatesSortByDateAsc())
                .thenReturn(expected);
        when(giftCertificateDAO.getAllListGiftCertificatesSortByDateDesc())
                .thenReturn(expected);

        List<GiftCertificate> actual = giftCertificateService.getAllListGiftCertificatesWithTags(sortMode);

        assertIterableEquals(expected, actual);
    }

    @Test
    void getListGiftCertificatesWithTagsByTagNameTest_RESOURCE_IN_NOT_EXIST() throws RepositoryException {
        String tagName = "tagName";
        SortMode sortMode = SortMode.NAME_ASC;

        when(giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameAsc(anyString()))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.getListGiftCertificatesWithTagsByTagName(tagName, sortMode);
        });
    }

    @Test
    void getListGiftCertificatesWithTagsByTagNameTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        String tagName = "tagName";
        SortMode sortMode = SortMode.NAME_ASC;

        when(giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameAsc(anyString()))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            giftCertificateService.getListGiftCertificatesWithTagsByTagName(tagName, sortMode);
        });
    }

    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void getListGiftCertificatesWithTagsByTagNameTest_SHOULD_RETURN_LIST(SortMode sortMode) throws RepositoryException, ResourceNotFoundException, ServiceException {
        String tagName = "tagName";
        List<GiftCertificate> expected = List.of(new GiftCertificate());

        when(giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameAsc(anyString()))
                .thenReturn(expected);
        when(giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameDesc(anyString()))
                .thenReturn(expected);
        when(giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateAsc(anyString()))
                .thenReturn(expected);
        when(giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateDesc(anyString()))
                .thenReturn(expected);

        List<GiftCertificate> actual = giftCertificateService.getListGiftCertificatesWithTagsByTagName(tagName, sortMode);

        assertIterableEquals(expected, actual);
    }

    @Test
    void getListGiftCertificatesWithTagsBySearchTest_RESOURCE_IN_NOT_EXIST() throws RepositoryException {
        String tagName = "tagName";
        SortMode sortMode = SortMode.NAME_ASC;

        when(giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(anyString()))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.getListGiftCertificatesWithTagsBySearch(tagName, sortMode);
        });
    }

    @Test
    void getListGiftCertificatesWithTagsBySearchTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        String tagName = "tagName";
        SortMode sortMode = SortMode.NAME_ASC;

        when(giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(anyString()))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            giftCertificateService.getListGiftCertificatesWithTagsBySearch(tagName, sortMode);
        });
    }

    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void getListGiftCertificatesWithTagsBySearchTest_SHOULD_RETURN_LIST(SortMode sortMode) throws RepositoryException, ResourceNotFoundException, ServiceException {
        String tagName = "tagName";
        List<GiftCertificate> expected = List.of(new GiftCertificate());

        when(giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(anyString()))
                .thenReturn(expected);
        when(giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(anyString()))
                .thenReturn(expected);
        when(giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(anyString()))
                .thenReturn(expected);
        when(giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(anyString()))
                .thenReturn(expected);

        List<GiftCertificate> actual = giftCertificateService.getListGiftCertificatesWithTagsBySearch(tagName, sortMode);

        assertIterableEquals(expected, actual);
    }

    @Test
    void getGiftCertificatesByIdTest_RESOURCE_IN_NOT_EXIST() throws RepositoryException {
        Long id = 1L;

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.getGiftCertificatesById(id);
        });
    }

    @Test
    void getGiftCertificatesByIdTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Long id = 1L;

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            giftCertificateService.getGiftCertificatesById(id);
        });
    }

    @Test
    void getGiftCertificatesByIdTest_SHOULD_RETURN_GIFT_CERTIFICATE() throws RepositoryException, ResourceNotFoundException, ServiceException {
        Long id = 1L;
        GiftCertificate expected = new GiftCertificate();

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        GiftCertificate actual = giftCertificateService.getGiftCertificatesById(id);

        assertEquals(expected, actual);
    }

    @Test
    void createTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateMock.getName()).thenReturn("");

        when(giftCertificateDAO.isAlreadyExistByName(anyString()))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            giftCertificateService.create(giftCertificateMock);
        });
    }

    @Test
    void createTest_GIFT_CERTIFICATE_ALREADY_EXIST_WITH_NAME() throws RepositoryException {
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateMock.getName()).thenReturn("");

        when(giftCertificateDAO.isAlreadyExistByName(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class, () -> {
            giftCertificateService.create(giftCertificateMock);
        });
    }

    @Test
    void createTest_SHOULD_CREATE_GIFT_CERTIFICATE() throws RepositoryException, ServiceException, ResourceAlreadyExistException {
        Long expected = 1L;

        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        when(giftCertificateMock.getName()).thenReturn("");
        when(giftCertificateMock.getTags()).thenReturn(Collections.emptyList());

        when(giftCertificateDAO.isAlreadyExistByName(anyString()))
                .thenReturn(false);

        when(giftCertificateDAO.create(any(GiftCertificate.class))).thenReturn(expected);

        Long actual = giftCertificateService.create(giftCertificateMock);

        assertEquals(expected, actual);
    }

    @Test
    void updateTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            giftCertificateService.update(giftCertificateMock, id);
        });
    }

    @Test
    void updateTest_GIFT_CERTIFICATE_IS_NOT_EXIST_WITH_ID() throws RepositoryException {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.update(giftCertificateMock, id);
        });
    }

    @Test
    void updateTest_SHOULD_UPDATE_GIFT_CERTIFICATE() throws RepositoryException, ResourceNotFoundException, ServiceException {
        Long id = 1L;
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);

        when(giftCertificateMock.getTags()).thenReturn(Collections.emptyList());

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(giftCertificateMock));

        doNothing().when(giftCertificateDAO).update(any(GiftCertificate.class));

        GiftCertificate actual = giftCertificateService.update(giftCertificateMock, id);

        assertNotNull(actual);
    }

    @Test
    void deleteTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Long id = 1L;

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            giftCertificateService.delete(id);
        });
    }

    @Test
    void deleteTest_GIFT_CERTIFICATE_IS_NOT_EXIST_WITH_ID() throws RepositoryException {
        Long id = 1L;

        when(giftCertificateDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateService.delete(id);
        });
    }

}
