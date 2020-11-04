package com.epam.esm.entity;

import com.epam.esm.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tag.
 */
@Data
public class Tag {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @EnglishLanguage(withPunctuations = false)
    private String name;

    private List<GiftCertificate> giftCertificates;

    /**
     * Instantiates a new Tag.
     */
    public Tag() {
        giftCertificates = new ArrayList<>();
    }

    /**
     * Add gift certificate.
     *
     * @param giftCertificate the gift certificate
     */
    public void addGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate != null) {
            giftCertificates.add(giftCertificate);
        }
    }

    /**
     * Delete gift certificate.
     *
     * @param giftCertificate the gift certificate
     */
    public void deleteGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate != null) {
            giftCertificates.removeIf(g -> g.getId().equals(giftCertificate.getId()));
        }
    }

}
