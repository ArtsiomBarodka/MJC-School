package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("TagService")
public class TagServiceImpl implements TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);

    private TagDAO tagDAO;

    private GiftCertificateTagDAO giftCertificateTagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, GiftCertificateTagDAO giftCertificateTagDAO) {
        this.tagDAO = tagDAO;
        this.giftCertificateTagDAO = giftCertificateTagDAO;
    }

    @Override
    public boolean isAlreadyExist(String tagName) throws ServiceException {
        try{
            return tagDAO.isAlreadyExistByName(tagName);
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t detect if tag exists at service layer.", ex);
            throw new ServiceException("Can`t detect if tag exists at service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional
    public Long create(Tag tag) throws ResourceAlreadyExistException, ServiceException {
        try{
            if (tagDAO.isAlreadyExistByName(tag.getName())){
                LOGGER.warn("Tag with name {} already exist", tag.getName());
                throw new ResourceAlreadyExistException(String.format("Tag with name %s already exist", tag.getName()));
            }
            Long id = tagDAO.create(tag);
            for (GiftCertificate giftCertificate : tag.getGiftCertificates()) {
                giftCertificateTagDAO.saveGiftCertificateTag(giftCertificate.getId(), id);
            }
            return id;
        } catch (RepositoryException ex) {
            LOGGER.error("Can`t create tag in service layer.", ex);
            throw new ServiceException("Can`t create tag in service layer.", ex, ex.getErrorCode());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException, ResourceNotFoundException {
        try{
            if(!tagDAO.findById(id).isPresent()){
                LOGGER.warn("Tag with id {} is not exist", id);
                throw new ResourceNotFoundException(String.format("Tag with %d is not exist", id));
            }
            tagDAO.delete(id);
        } catch (RepositoryException ex){
            LOGGER.error("Can`t delete tag in service layer.", ex);
            throw new ServiceException("Can`t tag tag in service layer.", ex, ex.getErrorCode());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTagById(Long id) throws ServiceException, ResourceNotFoundException {
        try{
            return tagDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Tag with id %d is not exist", id)));
        } catch (RepositoryException ex){
            LOGGER.error("Can`t get tag from service layer.", ex);
            throw new ServiceException("Can`t get tag from service layer.", ex, ex.getErrorCode());
        }
    }
}
