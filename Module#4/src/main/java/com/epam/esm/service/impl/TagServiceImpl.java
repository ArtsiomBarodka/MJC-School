package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TagServiceImpl implements TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagDAO tagDAO;
    private final GiftCertificateDAO giftCertificateDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, GiftCertificateDAO giftCertificateDAO) {
        this.tagDAO = tagDAO;
        this.giftCertificateDAO = giftCertificateDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(Tag tag) throws ResourceAlreadyExistException, BadParametersException {
        //check if the tag name already has in repository (must be unique)
        if (tagDAO.existsByName(tag.getName())) {
            LOGGER.warn("Tag with name {} already exist", tag.getName());
            throw new ResourceAlreadyExistException(String.format("Tag with name %s already exist", tag.getName()));
        }

        //save in repository
        Tag savedTag = tagDAO.save(tag);

        return savedTag.getId();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Tag getTheMostWidelyUsedTagOfUsersFromTheHighestCostOfAllOrders() throws ResourceNotFoundException {
        return tagDAO.findTheMostWidelyUsedOfUsersWithTheHighestCostOfAllOrders()
                .orElseThrow(() -> new ResourceNotFoundException("Tag is not exist"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws ResourceNotFoundException {
        if (!tagDAO.findById(id).isPresent()) {
            LOGGER.warn("Tag with id {} is not exist", id);
            throw new ResourceNotFoundException(String.format("Tag with %d is not exist", id));
        }
        tagDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Tag getById(Long id) throws ResourceNotFoundException {
        return tagDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tag with id %d is not exist", id)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tag update(Tag tag, Long id) throws ResourceNotFoundException, BadParametersException {
        //check if the tag name is exist in repository
        Tag repositoryTag = tagDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tag with id %d is not exist", id)));

        //check if the updated name unique
        if (tagDAO.existsByName(tag.getName()) &&
                !repositoryTag.getName().equalsIgnoreCase(tag.getName())) {
            LOGGER.warn("Tag with name {} is already exist", tag.getName());
            throw new BadParametersException(String.format("Tag with name %s is already exist", tag.getName()));
        }

        for (GiftCertificate giftCertificate : tag.getGiftCertificates()) {
            //check if the current gift certificate is exist in repository
            if (!giftCertificateDAO.existsById(giftCertificate.getId())) {
                LOGGER.warn("Gift certificate with id {} is not exist", giftCertificate.getId());
                throw new BadParametersException(String.format("Gift certificate with id %d is not exist", giftCertificate.getId()));
            }
        }

        //update fields of gift certificate and save it in repository
        repositoryTag.setName(tag.getName());
        repositoryTag.setGiftCertificates(tag.getGiftCertificates());

        return repositoryTag;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<Tag> getAll(Pageable pageable) throws ResourceNotFoundException {
        Page<Tag> result = tagDAO.findAll(pageable);

        if (!result.hasContent()) {
            LOGGER.warn("List of tags are not found");
            throw new ResourceNotFoundException("List of tags are not found");
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<Tag> getListByGiftCertificateId(Long id, Pageable pageable) throws ResourceNotFoundException {
        Page<Tag> result = tagDAO.getByGiftCertificates_Id(id, pageable);

        if (!result.hasContent()) {
            LOGGER.warn("List of tags are not found");
            throw new ResourceNotFoundException("List of tags are not found");
        }

        return result;
    }
}
