package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Tag service.
 */
@Service
public class TagServiceImpl implements TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagDAO tagDAO;
    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateTagDAO giftCertificateTagDAO;

    /**
     * Instantiates a new Tag service.
     *
     * @param tagDAO                the tag dao
     * @param giftCertificateTagDAO the gift certificate tag dao
     */
    @Autowired
    public TagServiceImpl(TagDAO tagDAO,
                          GiftCertificateTagDAO giftCertificateTagDAO,
                          GiftCertificateDAO giftCertificateDAO) {
        this.tagDAO = tagDAO;
        this.giftCertificateTagDAO = giftCertificateTagDAO;
        this.giftCertificateDAO = giftCertificateDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(Tag tag) throws ResourceAlreadyExistException, ServiceException, BadParametersException {
        try {
            //check if the tag name already has in repository (must be unique)
            if (tagDAO.isAlreadyExistByName(tag.getName())) {
                LOGGER.warn("Tag with name {} already exist", tag.getName());
                throw new ResourceAlreadyExistException(String.format("Tag with name %s already exist", tag.getName()));
            }

            //save tag to repository
            Long id = tagDAO.create(tag);

            //save relationship to repository
            for (GiftCertificate giftCertificate : tag.getGiftCertificates()) {

                //check if the current gift certificate is exist in repository
                if (!giftCertificateDAO.findById(giftCertificate.getId()).isPresent()) {
                    LOGGER.warn("Gift certificate with id {} is not exist", giftCertificate.getId());
                    throw new BadParametersException(String.format("Gift certificate with id %d is not exist", giftCertificate.getId()));
                }

                giftCertificateTagDAO.saveGiftCertificateTag(giftCertificate.getId(), id);
            }

            return id;
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t create tag in service layer.", ex);
            throw new ServiceException("Can`t create tag in service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws ServiceException, ResourceNotFoundException {
        try {
            if (!tagDAO.findById(id).isPresent()) {
                LOGGER.warn("Tag with id {} is not exist", id);
                throw new ResourceNotFoundException(String.format("Tag with %d is not exist", id));
            }
            tagDAO.delete(id);
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t delete tag in service layer.", ex);
            throw new ServiceException("Can`t tag tag in service layer.", ex, ex.getErrorCode());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTagById(Long id) throws ServiceException, ResourceNotFoundException {
        try {
            return tagDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Tag with id %d is not exist", id)));
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t get tag from service layer.", ex);
            throw new ServiceException("Can`t get tag from service layer.", ex, ex.getErrorCode());
        }
    }
}
