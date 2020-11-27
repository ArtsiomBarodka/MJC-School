package com.epam.esm.patch;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

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
