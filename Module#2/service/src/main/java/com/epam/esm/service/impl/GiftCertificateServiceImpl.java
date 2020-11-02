package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.repository.RepositoryException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Service("GiftCertificateService")
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);


    private GiftCertificateDAO giftCertificateDAO;

    private GiftCertificateTagDAO giftCertificateTagDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, GiftCertificateTagDAO giftCertificateTagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateTagDAO = giftCertificateTagDAO;
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

            if(result.isEmpty()){
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

            if(result.isEmpty()){
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

            if(result.isEmpty()){
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
    @Transactional
    public Long create(GiftCertificate giftCertificate) throws ServiceException, ResourceAlreadyExistException {
        try{
            if(giftCertificateDAO.isAlreadyExistByName(giftCertificate.getName())){
                LOGGER.warn("Gift certificate with name {} already exist", giftCertificate.getName());
                throw new ResourceAlreadyExistException(String.format("Gift certificate with name %s already exist", giftCertificate.getName()));
            }
            Long id = giftCertificateDAO.create(giftCertificate);
            for (Tag t : giftCertificate.getTags()) {
                giftCertificateTagDAO.saveGiftCertificateTag(id, t.getId());
            }
            return id;
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t create gift certificate in service layer.", ex);
            throw new ServiceException("Can`t create gift certificate in service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate giftCertificate, Long id)
            throws ServiceException, ResourceNotFoundException {

        try {
            GiftCertificate repositoryGiftCertificate = giftCertificateDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Gift certificate with id %d is not exist", id)));

            //update gift certificates fields without tags
            giftCertificate.setId(id);
            giftCertificateDAO.update(giftCertificate);

            List<Tag> repositoryTags = repositoryGiftCertificate.getTags();
            List<Tag> newTags = giftCertificate.getTags();

            //add new tags to database if not exists in db and updated gift certificate has new
            for (Tag tag : newTags) {
                if (!repositoryTags.contains(tag)) {
                    giftCertificateTagDAO.saveGiftCertificateTag(id, tag.getId());
                }
            }

            //delete tags from database if exists in db and updated gift certificate hasn`t
            for (Tag repositoryTag : repositoryTags) {
                if (!newTags.contains(repositoryTag)) {
                    giftCertificateTagDAO.deleteGiftCertificateTag(id, repositoryTag.getId());
                }
            }


            return giftCertificateDAO.findById(id)
                    .orElseThrow(()->new ServiceException("Can`t find gift certificate after update in service layer."));
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t update gift certificate in service layer.", ex);
            throw new ServiceException("Can`t update gift certificate in service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException, ResourceNotFoundException {
        try {
            if(!giftCertificateDAO.findById(id).isPresent()){
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
