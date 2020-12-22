package com.epam.esm.model.view;

import com.epam.esm.model.entity.GiftCertificate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.List;

/**
 * The type Gift certificate view.
 */
@Data
@Relation(collectionRelation = "giftCertificates")
@EqualsAndHashCode(callSuper = false)
public class GiftCertificateView extends RepresentationModel<GiftCertificateView> {
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Date createDate;

    private Date lastUpdateDate;

    private Integer duration;

    @JsonIgnoreProperties("giftCertificates")
    private List<TagView> tags;

    /**
     * From gift certificate to gift certificate view gift certificate view.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate view
     */
    public static GiftCertificateView fromGiftCertificateToGiftCertificateView(GiftCertificate giftCertificate) {
        GiftCertificateView giftCertificateView = new GiftCertificateView();
        giftCertificateView.setId(giftCertificate.getId());
        giftCertificateView.setName(giftCertificate.getName());
        giftCertificateView.setDescription(giftCertificate.getDescription());
        giftCertificateView.setPrice(giftCertificate.getPrice());
        giftCertificateView.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateView.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        giftCertificateView.setDuration(giftCertificate.getDuration());
        return giftCertificateView;
    }
}
