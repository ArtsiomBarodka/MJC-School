package com.epam.esm.impl;

import com.epam.esm.dao.TagDAO;
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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The type Tag service impl test.
 */
@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @Mock
    private TagDAO tagDAO;
    /**
     * The Tag service.
     */
    @InjectMocks
    TagServiceImpl tagService;

    /**
     * Create test repository layer throw exception.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void createTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Tag tagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("");

        when(tagDAO.isAlreadyExistByName(anyString()))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            tagService.create(tagMock);
        });
    }

    /**
     * Create test tag already exist with name.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void createTest_TAG_ALREADY_EXIST_WITH_NAME() throws RepositoryException {
        Tag tagMock = mock(Tag.class);

        when(tagMock.getName()).thenReturn("");

        when(tagDAO.isAlreadyExistByName(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class, () -> {
            tagService.create(tagMock);
        });
    }

    /**
     * Create test should create tag.
     *
     * @throws RepositoryException           the repository exception
     * @throws ServiceException              the service exception
     * @throws ResourceAlreadyExistException the resource already exist exception
     */
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

    /**
     * Delete test repository layer throw exception.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void deleteTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            tagService.delete(id);
        });
    }

    /**
     * Delete test tag is not exist with id.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void deleteTest_TAG_IS_NOT_EXIST_WITH_ID() throws RepositoryException {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.delete(id);
        });
    }

    /**
     * Gets tag by id test repository layer throw exception.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void getTagByIdTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenThrow(new RepositoryException());

        assertThrows(ServiceException.class, () -> {
            tagService.getTagById(id);
        });
    }

    /**
     * Gets tag by id test resource in not exist.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void getTagByIdTest_RESOURCE_IN_NOT_EXIST() throws RepositoryException {
        Long id = 1L;

        when(tagDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.getTagById(id);
        });
    }

    /**
     * Gets tag by id test should return tag.
     *
     * @throws RepositoryException       the repository exception
     * @throws ResourceNotFoundException the resource not found exception
     * @throws ServiceException          the service exception
     */
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
