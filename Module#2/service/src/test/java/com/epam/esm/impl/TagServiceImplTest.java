package com.epam.esm.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TagServiceImplTest {
    @Mock
    private TagDAO tagDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @InjectMocks
    TagServiceImpl tagService;

    @Test
    void createTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Tag tagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("");

        when(tagDAO.isAlreadyExistByName(anyString()))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, ()->{
            tagService.create(tagMock);
        });
    }

    @Test
    void createTest_TAG_ALREADY_EXIST_WITH_NAME() throws RepositoryException {
        Tag tagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("");

        when(tagDAO.isAlreadyExistByName(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class, ()->{
            tagService.create(tagMock);
        });
    }

    @Test
    void createTest_SHOULD_CREATE_TAG() throws RepositoryException, ServiceException, ResourceAlreadyExistException {
        Long expected = 1L;

        Tag tagMock = mock(Tag.class);
        when(tagMock.getName()).thenReturn("");
        when(tagMock.getGiftCertificates()).thenReturn(Collections.emptyList());

        when(tagDAO.isAlreadyExistByName(anyString()))
                .thenReturn(false);

        when(tagDAO.create(any(Tag.class))).thenReturn(expected);

        Long actual = tagService.create(tagMock);

        assertEquals(expected, actual);
    }

    @Test
    void deleteTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, ()->{
            tagService.delete(id);
        });
    }

    @Test
    void deleteTest_TAG_IS_NOT_EXIST_WITH_ID() throws RepositoryException {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            tagService.delete(id);
        });
    }

    @Test
    void getTagByIdTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, ()->{
            tagService.getTagById(id);
        });
    }

    @Test
    void getTagByIdTest_RESOURCE_IN_NOT_EXIST() throws RepositoryException {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            tagService.getTagById(id);
        });
    }

    @Test
    void getTagByIdTest_SHOULD_RETURN_TAG() throws RepositoryException, ResourceNotFoundException, ServiceException {
        Long id = 1L;
        Tag expected = new Tag();

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        Tag actual = tagService.getTagById(id);

        assertEquals(expected, actual);
    }
}
