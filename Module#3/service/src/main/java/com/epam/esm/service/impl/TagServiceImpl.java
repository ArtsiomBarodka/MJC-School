package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Pageable;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


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
        if (tagDAO.isExistByName(tag.getName())) {
            LOGGER.warn("Tag with name {} already exist", tag.getName());
            throw new ResourceAlreadyExistException(String.format("Tag with name %s already exist", tag.getName()));
        }

        Tag savedTag = tagDAO.save(tag);

        for (GiftCertificate giftCertificate : tag.getGiftCertificates()) {
            //check if the current gift certificate is exist in repository
            savedTag.addGiftCertificates(giftCertificateDAO.findById(giftCertificate.getId()).orElseThrow(()->{
                LOGGER.warn("Gift certificate with id {} is not exist", giftCertificate.getId());
                return new BadParametersException(String.format("Gift certificate with id %d is not exist", giftCertificate.getId()));
            }));
        }

        return savedTag.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws ResourceNotFoundException {
        if (!tagDAO.findById(id).isPresent()) {
            LOGGER.warn("Tag with id {} is not exist", id);
            throw new ResourceNotFoundException(String.format("Tag with %d is not exist", id));
        }
        tagDAO.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTagById(Long id) throws ResourceNotFoundException {
        return tagDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tag with id %d is not exist", id)));
    }

    @Override
    public Tag update(Tag tag, Long id) throws ResourceNotFoundException, BadParametersException {
        //check if the tag name is exist in repository
        Tag repositoryTag = tagDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tag with id %d is not exist", id)));

        //check if the updated name unique
        if (tagDAO.isExistByName(tag.getName()) &&
                !repositoryTag.getName().equals(tag.getName())) {
            LOGGER.warn("Tag with name {} is already exist", tag.getName());
            throw new BadParametersException(String.format("Tag with name %s is already exist", tag.getName()));
        }

        for (GiftCertificate giftCertificate : tag.getGiftCertificates()) {
            //check if the current gift certificate is exist in repository
            if (!giftCertificateDAO.isExistById(giftCertificate.getId())) {
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
    public List<Tag> getListAllTagsWithGiftCertificates(Pageable pageable, SortMode sortMode) throws ResourceNotFoundException {
        List<Tag> result;
        switch (sortMode) {
            case ID_ASC:
                result = tagDAO.listAllTagsSortByIdAsc(pageable);
                break;

            case ID_DESC:
                result = tagDAO.listAllTagsSortByIdDesc(pageable);
                break;

            case NAME_ASC:
                result = tagDAO.listAllTagsSortByNameAsc(pageable);
                break;

            case NAME_DESC:
                result = tagDAO.listAllTagsSortByNameDesc(pageable);
                break;

            default:
                return Collections.emptyList();
        }

        if (result.isEmpty()) {
            LOGGER.warn("List of tags are not found");
            throw new ResourceNotFoundException("List of tags are not found");
        }

        return result;
    }

    @Override
    public List<Tag> getListTagsWithGiftCertificatesByGiftCertificateId(Long id, Pageable pageable, SortMode sortMode) throws ResourceNotFoundException {
        List<Tag> result;
        switch (sortMode) {
            case ID_ASC:
                result = tagDAO.listTagsByGiftCertificateIdSortByIdAsc(id, pageable);
                break;

            case ID_DESC:
                result = tagDAO.listTagsByGiftCertificateIdSortByIdDesc(id, pageable);
                break;

            case NAME_ASC:
                result = tagDAO.listTagsByGiftCertificateIdSortByNameAsc(id, pageable);
                break;

            case NAME_DESC:
                result = tagDAO.listTagsByGiftCertificateIdSortByNameDesc(id, pageable);
                break;

            default:
                return Collections.emptyList();
        }

        if (result.isEmpty()) {
            LOGGER.warn("List of tags are not found");
            throw new ResourceNotFoundException("List of tags are not found");
        }

        return result;
    }
}
