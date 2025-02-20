package com.epam.esm.patch;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * The type Patch gift certificate.
 */
@Data
public class PatchGiftCertificate implements PatchOperation<GiftCertificate> {
    @Size(min = 3, max = 20)
    @EnglishLanguage(withPunctuations = false)
    private String name;

    @EnglishLanguage
    private String description;

    @Positive
    private Double price;

    @Positive
    private Integer duration;

    private List<Tag> tags;

    @Override
    public void mergeToEntity(GiftCertificate existing) {
        if (name != null) {
            existing.setName(name);
        }
        if (description != null) {
            existing.setDescription(description);
        }
        if (price != null) {
            existing.setPrice(price);
        }
        if (duration != null) {
            existing.setDuration(duration);
        }
        if (tags != null) {
            existing.setTags(tags);
        }
    }
}
