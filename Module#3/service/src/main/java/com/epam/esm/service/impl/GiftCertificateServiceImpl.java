package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * The type Gift certificate service.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;
    @Autowired
    private TagDAO tagDAO;

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> getAllListGiftCertificatesWithTags(Page page, SortMode sortMode) throws ResourceNotFoundException {
        List<GiftCertificate> result = giftCertificateDAO.listAllGiftCertificates(page, sortMode);

        if (result.isEmpty()) {
            LOGGER.warn("List of gift certificates are not found");
            throw new ResourceNotFoundException("List of gift certificates are not found");
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> getListGiftCertificatesWithTagsByTagNames(List<String> tagName, Page page, SortMode sortMode)
            throws ResourceNotFoundException {

        List<GiftCertificate> result = giftCertificateDAO.listAllGiftCertificatesByTagNames(tagName, page, sortMode);

        if (result.isEmpty()) {
            LOGGER.warn("List of gift certificates with tag names {} not found", tagName);
            throw new ResourceNotFoundException(String.format("List of gift certificates with tag names %s not found", tagName));
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificate getGiftCertificatesById(Long id) throws ResourceNotFoundException {
        return giftCertificateDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Gift certificate with id %d is not exist", id)));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GiftCertificate giftCertificate)
            throws ResourceAlreadyExistException, BadParametersException {
        //check if the gift certificate name already has in repository (must be unique)
        if (giftCertificateDAO.isExistByName(giftCertificate.getName())) {
            LOGGER.warn("Gift certificate with name {} already exist", giftCertificate.getName());
            throw new ResourceAlreadyExistException(String.format("Gift certificate with name %s already exist", giftCertificate.getName()));
        }

        for (Tag t : giftCertificate.getTags()) {
            //check if the current tag is exist in repository
            if (!tagDAO.isExistById(t.getId())) {
                LOGGER.warn("Tag with id {} is not exist", t.getId());
                throw new BadParametersException(String.format("Tag with id %d is not exist", t.getId()));
            }
        }

        //save gift certificate to repository and return id;
        return giftCertificateDAO.save(giftCertificate).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void update(GiftCertificate giftCertificate, Long id)
            throws ResourceNotFoundException, BadParametersException {
        //check if the gift certificate is exist in repository
        GiftCertificate repositoryGiftCertificate = giftCertificateDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Gift certificate with id %d is not exist", id)));

        //check if the updated name unique
        if(giftCertificateDAO.isExistByName(giftCertificate.getName()) &&
                !repositoryGiftCertificate.getName().equals(giftCertificate.getName())){
            LOGGER.warn("Gift Certificate with name {} is already exist", giftCertificate.getName());
            throw new BadParametersException(String.format("Gift Certificate with name %s is already exist", giftCertificate.getName()));
        }

        for (Tag tag : giftCertificate.getTags()) {
            //check if the current tag is exist in repository
            if(!tagDAO.isExistById(tag.getId())) {
                LOGGER.warn("Tag with id {} is not exist", tag.getId());
                throw new BadParametersException(String.format("Tag with id %d is not exist", tag.getId()));
            }
        }

        //update fields of gift certificate and save it in repository
        repositoryGiftCertificate.setDuration(giftCertificate.getDuration());
        repositoryGiftCertificate.setPrice(giftCertificate.getPrice());
        repositoryGiftCertificate.setName(giftCertificate.getName());
        repositoryGiftCertificate.setTags(giftCertificate.getTags());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GiftCertificate updateAndReturn(GiftCertificate giftCertificate, Long id) throws ResourceNotFoundException, BadParametersException, ServiceException {
        update(giftCertificate, id);
        return giftCertificateDAO.findById(id)
                .orElseThrow(() -> new ServiceException("Can`t find updated gift certificate by id after update"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws ResourceNotFoundException {

        if (!giftCertificateDAO.findById(id).isPresent()) {
            LOGGER.warn("Gift certificate with {} is not exist", id);
            throw new ResourceNotFoundException(String.format("Gift certificate with %d is not exist", id));
        }
        giftCertificateDAO.delete(id);
    }
}
