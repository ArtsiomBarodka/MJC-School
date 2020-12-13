package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.specification.GiftCertificateByTagsSpecification;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<GiftCertificate> getAll(Pageable pageable)
            throws ResourceNotFoundException {

        Page<GiftCertificate> result = giftCertificateDAO.findAll(pageable);

        if (!result.hasContent()) {
            LOGGER.warn("List of gift certificates are not found");
            throw new ResourceNotFoundException("List of gift certificates are not found");
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<GiftCertificate> getListByTagNames(List<String> names, Pageable pageable)
            throws ResourceNotFoundException {

        Iterable<Tag> byNameIn = tagDAO.getByNameIn(names);

        GiftCertificateByTagsSpecification giftCertificateByTagsSpecification = new GiftCertificateByTagsSpecification(byNameIn);
        Page<GiftCertificate> result = giftCertificateDAO.findAll(giftCertificateByTagsSpecification,pageable);

        if (!result.hasContent()) {
            LOGGER.warn("List of gift certificates with tag names {} not found", names);
            throw new ResourceNotFoundException(String.format("List of gift certificates with tag names %s not found", names));
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public GiftCertificate getById(Long id) throws ResourceNotFoundException {
        return giftCertificateDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Gift certificate with id %d is not exist", id)));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GiftCertificate giftCertificate)
            throws ResourceAlreadyExistException, BadParametersException {
        //check if the gift certificate name already has in repository (must be unique)
        if (giftCertificateDAO.existsByName(giftCertificate.getName())) {
            LOGGER.warn("Gift certificate with name {} already exist", giftCertificate.getName());
            throw new ResourceAlreadyExistException(String.format("Gift certificate with name %s already exist", giftCertificate.getName()));
        }

        for (Tag t : giftCertificate.getTags()) {
            //check if the current tag is exist in repository
            if (!tagDAO.existsById(t.getId())) {
                LOGGER.warn("Tag with id {} is not exist", t.getId());
                throw new BadParametersException(String.format("Tag with id %d is not exist", t.getId()));
            }
        }

        //save gift certificate to repository and return id;
        return giftCertificateDAO.save(giftCertificate).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GiftCertificate update(GiftCertificate giftCertificate, Long id)
            throws ResourceNotFoundException, BadParametersException {
        //check if the gift certificate is exist in repository
        GiftCertificate repositoryGiftCertificate = giftCertificateDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Gift certificate with id %d is not exist", id)));

        //check if the updated name unique
        if (giftCertificateDAO.existsByName(giftCertificate.getName()) &&
                !repositoryGiftCertificate.getName().equalsIgnoreCase(giftCertificate.getName())) {
            LOGGER.warn("Gift Certificate with name {} is already exist", giftCertificate.getName());
            throw new BadParametersException(String.format("Gift Certificate with name %s is already exist", giftCertificate.getName()));
        }

        for (Tag tag : giftCertificate.getTags()) {
            //check if the current tag is exist in repository
            if(!tagDAO.existsById(tag.getId())) {
                LOGGER.warn("Tag with id {} is not exist", tag.getId());
                throw new BadParametersException(String.format("Tag with id %d is not exist", tag.getId()));
            }
        }

        //update fields of gift certificate and save it in repository
        repositoryGiftCertificate.setDuration(giftCertificate.getDuration());
        repositoryGiftCertificate.setPrice(giftCertificate.getPrice());
        repositoryGiftCertificate.setName(giftCertificate.getName());
        repositoryGiftCertificate.setTags(giftCertificate.getTags());

        return repositoryGiftCertificate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws ResourceNotFoundException {
        if (!giftCertificateDAO.findById(id).isPresent()) {
            LOGGER.warn("Gift certificate with {} is not exist", id);
            throw new ResourceNotFoundException(String.format("Gift certificate with %d is not exist", id));
        }
        giftCertificateDAO.deleteById(id);
    }
}
