package com.epam.esm.model.patch;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * The type Patch tag.
 */
@Data
public class PatchTag implements PatchOperation<Tag> {
    @Size(min = 3, max = 20)
    @EnglishLanguage(withPunctuations = false)
    private String name;

    private List<GiftCertificate> giftCertificates;

    @Override
    public void mergeToEntity(Tag existing) {
        if (name != null) {
            existing.setName(name);
        }
        if (giftCertificates != null) {
            existing.setGiftCertificates(giftCertificates);
        }
    }
}
