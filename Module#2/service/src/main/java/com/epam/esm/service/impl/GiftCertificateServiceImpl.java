package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


/**
 * The type Gift certificate service.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);


    private final GiftCertificateDAO giftCertificateDAO;

    private final GiftCertificateTagDAO giftCertificateTagDAO;

    private final TagDAO tagDAO;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateDAO    the gift certificate dao
     * @param giftCertificateTagDAO the gift certificate tag dao
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO,
                                      GiftCertificateTagDAO giftCertificateTagDAO,
                                      TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateTagDAO = giftCertificateTagDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> getAllListGiftCertificatesWithTags(SortMode sortMode)
            throws ServiceException, ResourceNotFoundException {

        try {
            List<GiftCertificate> result;
            switch (sortMode) {
                case DATE_ASC:
                    result = giftCertificateDAO.getAllListGiftCertificatesSortByDateAsc();
                    break;

                case DATE_DESC:
                    result = giftCertificateDAO.getAllListGiftCertificatesSortByDateDesc();
                    break;

                case NAME_ASC:
                    result = giftCertificateDAO.getAllListGiftCertificatesSortByNameAsc();
                    break;

                case NAME_DESC:
                    result = giftCertificateDAO.getAllListGiftCertificatesSortByNameDesc();
                    break;

                default:
                    return Collections.emptyList();
            }

            if (result.isEmpty()) {
                LOGGER.warn("List of all gift certificates with tags  not found");
                throw new ResourceNotFoundException("List of all gift certificates with tags  not found");
            }

            return result;
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t get gift certificates list from service layer.", ex);
            throw new ServiceException("Can`t get gift certificates list from service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> getListGiftCertificatesWithTagsByTagName(String tagName, SortMode sortMode)
            throws ServiceException, ResourceNotFoundException {

        try {
            List<GiftCertificate> result;
            switch (sortMode) {
                case DATE_ASC:
                    result = giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateAsc(tagName);
                    break;

                case DATE_DESC:
                    result = giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateDesc(tagName);
                    break;

                case NAME_ASC:
                    result = giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameAsc(tagName);
                    break;

                case NAME_DESC:
                    result = giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameDesc(tagName);
                    break;

                default:
                    return Collections.emptyList();
            }

            if (result.isEmpty()) {
                LOGGER.warn("List of gift certificates with tag name {} not found", tagName);
                throw new ResourceNotFoundException(String.format("List of gift certificates with tag name %s not found", tagName));
            }

            return result;
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t get gift certificates list from service layer.", ex);
            throw new ServiceException("Can`t get gift certificates list from service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> getListGiftCertificatesWithTagsBySearch(String key, SortMode sortMode)
            throws ServiceException, ResourceNotFoundException {

        try {
            List<GiftCertificate> result;
            switch (sortMode) {
                case DATE_ASC:
                    result = giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(key);
                    break;

                case DATE_DESC:
                    result = giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(key);
                    break;

                case NAME_ASC:
                    result = giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(key);
                    break;

                case NAME_DESC:
                    result = giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(key);
                    break;

                default:
                    result = Collections.emptyList();
            }

            if (result.isEmpty()) {
                LOGGER.warn("List of gift certificates with query {} not found", key);
                throw new ResourceNotFoundException(String.format("List of gift certificates with query %s not found", key));
            }

            return result;
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t get gift certificates list from service layer.", ex);
            throw new ServiceException("Can`t get gift certificates list from service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificate getGiftCertificatesById(Long id)
            throws ResourceNotFoundException, ServiceException {

        try {
            return giftCertificateDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Gift certificate with id %d is not exist", id)));
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t get gift certificate from service layer.", ex);
            throw new ServiceException("Can`t get gift certificate from service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GiftCertificate giftCertificate)
            throws ServiceException, ResourceAlreadyExistException, BadParametersException {
        try {
            //check if the gift certificate name already has in repository (must be unique)
            if (giftCertificateDAO.isAlreadyExistByName(giftCertificate.getName())) {
                LOGGER.warn("Gift certificate with name {} already exist", giftCertificate.getName());
                throw new ResourceAlreadyExistException(String.format("Gift certificate with name %s already exist", giftCertificate.getName()));
            }

            //save gift certificate to repository
            Long id = giftCertificateDAO.create(giftCertificate);

            //save relationship to repository
            for (Tag t : giftCertificate.getTags()) {

                //check if the current tag is exist in repository
                if (!tagDAO.findById(t.getId()).isPresent()) {
                    LOGGER.warn("Tag with id {} is not exist", t.getId());
                    throw new BadParametersException(String.format("Tag with id %d is not exist", t.getId()));
                }

                giftCertificateTagDAO.saveGiftCertificateTag(id, t.getId());
            }
            return id;
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t create gift certificate in service layer.", ex);
            throw new ServiceException("Can`t create gift certificate in service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GiftCertificate update(GiftCertificate giftCertificate, Long id)
            throws ServiceException, ResourceNotFoundException, BadParametersException {

        try {
            //check if the gift certificate exist in repository
            GiftCertificate repositoryGiftCertificate = giftCertificateDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Gift certificate with id %d is not exist", id)));

            //update gift certificates without tags
            giftCertificate.setId(id);
            giftCertificateDAO.update(giftCertificate);

            List<Tag> repositoryTags = repositoryGiftCertificate.getTags();
            List<Tag> newTags = giftCertificate.getTags();

            //add new tags to database if not exists in db and exists in updated gift certificate
            for (Tag tag : newTags) {
                if (!repositoryTags.contains(tag)) {

                    //check if the current tag is exist in repository
                    if (!tagDAO.findById(tag.getId()).isPresent()) {
                        LOGGER.warn("Tag with id {} is not exist", tag.getId());
                        throw new BadParametersException(String.format("Tag with id %d is not exist", tag.getId()));
                    }
                    giftCertificateTagDAO.saveGiftCertificateTag(id, tag.getId());
                }
            }

            //delete tags from database if exists in db and not exists in updated gift certificate
            for (Tag repositoryTag : repositoryTags) {
                if (!newTags.contains(repositoryTag)) {
                    giftCertificateTagDAO.deleteGiftCertificateTag(id, repositoryTag.getId());
                }
            }

            return giftCertificateDAO.findById(id)
                    .orElseThrow(() -> new ServiceException("Can`t find gift certificate after update in service layer."));
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t update gift certificate in service layer.", ex);
            throw new ServiceException("Can`t update gift certificate in service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws ServiceException, ResourceNotFoundException {
        try {
            if (!giftCertificateDAO.findById(id).isPresent()) {
                LOGGER.warn("Gift certificate with {} is not exist", id);
                throw new ResourceNotFoundException(String.format("Gift certificate with %d is not exist", id));
            }
            giftCertificateDAO.delete(id);
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t delete gift certificate in service layer.", ex);
            throw new ServiceException("Can`t delete gift certificate in service layer.", ex, ex.getErrorCode());
        }
    }
}
