package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

/**
 * The type Tag service impl test.
 */
//@ExtendWith(MockitoExtension.class)
//public class TagServiceImplTest {
//    @Mock
//    private TagDAO tagDAO;
//    @Mock
//    private GiftCertificateDAO giftCertificateDAO;
//    /**
//     * The Tag service.
//     */
//    @InjectMocks
//    TagServiceImpl tagService;
//
//    /**
//     * Create test repository layer throw exception.
//     *
//     * @throws RepositoryException the repository exception
//     */
//    @Test
//    void createTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
//        Tag tagMock = mock(Tag.class);
//
//        when(tagMock.getName()).thenReturn("");
//
//        when(tagDAO.isAlreadyExistByName(anyString()))
//                .thenThrow(new RepositoryException());
//
//        assertThrows(ServiceException.class, () -> {
//            tagService.create(tagMock);
//        });
//    }
//
//    /**
//     * Create test tag already exist with name.
//     *
//     * @throws RepositoryException the repository exception
//     */
//    @Test
//    void createTest_TAG_ALREADY_EXIST_WITH_NAME() throws RepositoryException {
//        Tag tagMock = mock(Tag.class);
//
//        when(tagMock.getName()).thenReturn("");
//
//        when(tagDAO.isAlreadyExistByName(anyString()))
//                .thenReturn(true);
//
//        assertThrows(ResourceAlreadyExistException.class, () -> {
//            tagService.create(tagMock);
//        });
//    }
//
//    /**
//     * Create test current gift certificate is not exist.
//     *
//     * @throws RepositoryException the repository exception
//     */
//    @Test
//    void createTest_CURRENT_GIFT_CERTIFICATE_IS_NOT_EXIST() throws RepositoryException {
//        Tag tagMock = mock(Tag.class);
//        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
//        List<GiftCertificate> giftCertificates = new ArrayList<>();
//        giftCertificates.add(giftCertificateMock);
//
//        when(tagMock.getName()).thenReturn("");
//        when(tagMock.getGiftCertificates()).thenReturn(giftCertificates);
//        when(giftCertificateMock.getId()).thenReturn(1L);
//
//        when(tagDAO.isAlreadyExistByName(anyString()))
//                .thenReturn(false);
//
//        when(tagDAO.create(any(Tag.class)))
//                .thenReturn(1L);
//
//        when(giftCertificateDAO.findById(any(Long.TYPE)))
//                .thenReturn(Optional.empty());
//
//        assertThrows(BadParametersException.class, () -> {
//            tagService.create(tagMock);
//        });
//    }
//
//    /**
//     * Create test should create tag.
//     *
//     * @throws RepositoryException           the repository exception
//     * @throws ServiceException              the service exception
//     * @throws ResourceAlreadyExistException the resource already exist exception
//     * @throws BadParametersException        the bad parameters exception
//     */
//    @Test
//    void createTest_SHOULD_CREATE_TAG() throws RepositoryException, ServiceException, ResourceAlreadyExistException, BadParametersException {
//        Long expected = 1L;
//
//        Tag tagMock = mock(Tag.class);
//        when(tagMock.getName()).thenReturn("");
//        when(tagMock.getGiftCertificates()).thenReturn(Collections.emptyList());
//
//        when(tagDAO.isAlreadyExistByName(anyString()))
//                .thenReturn(false);
//
//        when(tagDAO.create(any(Tag.class))).thenReturn(expected);
//
//        Long actual = tagService.create(tagMock);
//
//        assertEquals(expected, actual);
//    }
//
//    /**
//     * Delete test repository layer throw exception.
//     *
//     * @throws RepositoryException the repository exception
//     */
//    @Test
//    void deleteTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
//        Long id = 1L;
//
//        when(tagDAO.findById(any(Long.TYPE)))
//                .thenThrow(new RepositoryException());
//
//        assertThrows(ServiceException.class, () -> {
//            tagService.delete(id);
//        });
//    }
//
//    /**
//     * Delete test tag is not exist with id.
//     *
//     * @throws RepositoryException the repository exception
//     */
//    @Test
//    void deleteTest_TAG_IS_NOT_EXIST_WITH_ID() throws RepositoryException {
//        Long id = 1L;
//
//        when(tagDAO.findById(any(Long.TYPE)))
//                .thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            tagService.delete(id);
//        });
//    }
//
//    /**
//     * Gets tag by id test repository layer throw exception.
//     *
//     * @throws RepositoryException the repository exception
//     */
//    @Test
//    void getTagByIdTest_REPOSITORY_LAYER_THROW_EXCEPTION() throws RepositoryException {
//        Long id = 1L;
//
//        when(tagDAO.findById(any(Long.TYPE)))
//                .thenThrow(new RepositoryException());
//
//        assertThrows(ServiceException.class, () -> {
//            tagService.getTagById(id);
//        });
//    }
//
//    /**
//     * Gets tag by id test resource in not exist.
//     *
//     * @throws RepositoryException the repository exception
//     */
//    @Test
//    void getTagByIdTest_RESOURCE_IN_NOT_EXIST() throws RepositoryException {
//        Long id = 1L;
//
//        when(tagDAO.findById(any(Long.TYPE)))
//                .thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            tagService.getTagById(id);
//        });
//    }
//
//    /**
//     * Gets tag by id test should return tag.
//     *
//     * @throws RepositoryException       the repository exception
//     * @throws ResourceNotFoundException the resource not found exception
//     * @throws ServiceException          the service exception
//     */
//    @Test
//    void getTagByIdTest_SHOULD_RETURN_TAG() throws RepositoryException, ResourceNotFoundException, ServiceException {
//        Long id = 1L;
//        Tag expected = new Tag();
//
//        when(tagDAO.findById(any(Long.TYPE)))
//                .thenReturn(Optional.of(expected));
//
//        Tag actual = tagService.getTagById(id);
//
//        assertEquals(expected, actual);
//    }
//}
