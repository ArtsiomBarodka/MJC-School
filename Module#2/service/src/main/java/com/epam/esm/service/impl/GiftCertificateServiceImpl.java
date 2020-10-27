package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service("GiftCertificateService")
public class GiftCertificateServiceImpl implements GiftCertificateService {
    @Autowired
    private GiftCertificateDAO giftCertificateDAO;
    @Autowired
    private GiftCertificateTagDAO giftCertificateTagDAO;

    @Override
    public boolean isAlreadyExist(String giftCertificateName) {
        return giftCertificateDAO.isAlreadyExistByName(giftCertificateName);
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesWithTagsByTagName(String tagName, SortMode sortMode) {
        switch (sortMode) {
            case DATE_ASC:
                return giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateAsc(tagName);

            case DATE_DESC:
                return giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateDesc(tagName);

            case NAME_ASC:
                return giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameAsc(tagName);

            case NAME_DESC:
                return giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameDesc(tagName);

            default:
                return Collections.emptyList();
        }
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesWithTagsBySearch(String key, SortMode sortMode) {
        switch (sortMode) {
            case DATE_ASC:
                return giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(key);

            case DATE_DESC:
                return giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(key);

            case NAME_ASC:
                return giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(key);

            case NAME_DESC:
                return giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(key);

            default:
                return Collections.emptyList();
        }
    }

    @Override
    public Optional<GiftCertificate> getGiftCertificatesById(Long id) {
        return giftCertificateDAO.findById(id);
    }

    @Override
    @Transactional
    public Long create(GiftCertificate giftCertificate) {
        Long id = giftCertificateDAO.create(giftCertificate);
        for (Tag t : giftCertificate.getTags()) {
            giftCertificateTagDAO.saveGiftCertificateTag(id, t.getId());
        }
        return id;
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate giftCertificate, Long id) {
        //update gift certificates fields without tags
        giftCertificateDAO.update(giftCertificate);
        List<Tag> repositoryTags = giftCertificateDAO.findById(id).get().getTags();
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

        return giftCertificateDAO.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        giftCertificateDAO.delete(id);
    }
}
