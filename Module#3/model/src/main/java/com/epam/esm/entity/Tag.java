package com.epam.esm.entity;

import com.epam.esm.validation.annotation.EnglishLanguage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tag.
 */
@Data
@Entity
@Table(name = "tag")
public class Tag extends RepresentationModel<Tag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @EnglishLanguage(withSpecSymbols = false, withNumbers = false)
    @Column
    private String name;

    @ToString.Exclude
    @JsonIgnoreProperties("tags")
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<GiftCertificate> giftCertificates;


    /**
     * Instantiates a new Tag.
     */
    public Tag() {
        giftCertificates = new ArrayList<>();
    }

    /**
     * Add gift certificates.
     *
     * @param giftCertificate the gift certificate
     */
    public void addGiftCertificates(GiftCertificate giftCertificate) {
        if (giftCertificate != null) {
            giftCertificates.add(giftCertificate);
            giftCertificate.getTags().add(this);
        }
    }


    /**
     * Delete gift certificates.
     *
     * @param giftCertificate the gift certificate
     */
    public void deleteGiftCertificates(GiftCertificate giftCertificate) {
        if (giftCertificate != null) {
            giftCertificates.removeIf(g -> g.getId().equals(giftCertificate.getId()));
            giftCertificate.getTags().removeIf(t -> t.getId().equals(this.id));
        }
    }

}
