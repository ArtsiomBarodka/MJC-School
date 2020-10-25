package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.FindGiftCertificateAndTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("FindGiftCertificateAndTagService")
public class FindGiftCertificateAndTagServiceImpl implements FindGiftCertificateAndTagService {
    private GiftCertificateDAO giftCertificateDAO;
    private TagDAO tagDAO;

    @Autowired
    FindGiftCertificateAndTagServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO){
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> getListGiftCertificatesWithTagsByTagName(String tagName,
                                                                          SortMode sortMode) {
        switch (sortMode){
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
    public List<GiftCertificate> getListGiftCertificatesWithTagsSearchByGiftCertificateNameOrDescription(String query,
                                                                                                         SortMode sortMode) {
        switch (sortMode){
            case DATE_ASC:
                return giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(query);

            case DATE_DESC:
                return giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(query);

            case NAME_ASC:
                return giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(query);

            case NAME_DESC:
                return giftCertificateDAO.getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(query);

            default:
                return Collections.emptyList();
        }
    }

    @Override
    public GiftCertificate getGiftCertificateById(long id) {
        return giftCertificateDAO.findById(id).get();
//        return giftCertificate.orElseThrow(new ObjectNotFoundException(@Value({exception.giftCertificateNotFound, id})));
    }

    @Override
    public List<Tag> getListTagsWithGiftCertificatesByGiftCertificateId(long id) {
        return null;
    }

    @Override
    public Tag getTagById(long id) {
        return null;
    }
}
