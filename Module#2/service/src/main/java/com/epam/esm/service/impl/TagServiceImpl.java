package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("TagService")
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private GiftCertificateTagDAO giftCertificateTagDAO;

    @Override
    public boolean isAlreadyExist(String tagName) {
        return tagDAO.isAlreadyExistByName(tagName);
    }

    @Override
    @Transactional
    public Long create(Tag tag) {
        Long id = tagDAO.create(tag);
        for (GiftCertificate giftCertificate : tag.getGiftCertificates()) {
            giftCertificateTagDAO.saveGiftCertificateTag(giftCertificate.getId(), id);
        }
        return id;
    }

    @Override
    public void delete(Long id) {
        tagDAO.delete(id);
    }

    @Override
    public Optional<Tag> getTagById(Long id) {
        return tagDAO.findById(id);
    }
}
