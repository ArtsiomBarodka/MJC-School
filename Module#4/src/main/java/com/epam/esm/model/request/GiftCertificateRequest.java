package com.epam.esm.model.request;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Gift certificate request.
 */
@Data
public class GiftCertificateRequest {
    @NotNull
    @Size(min = 3, max = 50)
    @EnglishLanguage(withNumbers = false, withSpecSymbols = false)
    private String name;

    @NotNull
    @EnglishLanguage
    private String description;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Integer duration;

    @Size(min = 1)
    private List<@NotNull @Positive Long> tags = new ArrayList<>();

    /**
     * To gift certificate gift certificate.
     *
     * @param giftCertificateRequest the gift certificate request
     * @return the gift certificate
     */
    public static GiftCertificate toGiftCertificate(GiftCertificateRequest giftCertificateRequest) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(giftCertificateRequest.getName());
        giftCertificate.setDescription(giftCertificateRequest.getDescription());
        giftCertificate.setPrice(giftCertificateRequest.getPrice());
        giftCertificate.setDuration(giftCertificateRequest.getDuration());
        giftCertificate.setTags(giftCertificateRequest.getTags()
                .stream()
                .map(id -> {
                    Tag tag = new Tag();
                    tag.setId(id);
                    return tag;
                }).collect(Collectors.toList()));
        return giftCertificate;
    }
}
