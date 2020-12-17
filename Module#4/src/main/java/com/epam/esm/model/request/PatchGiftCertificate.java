package com.epam.esm.model.request;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PatchGiftCertificate implements PatchOperation<GiftCertificate> {
    @Size(min = 3, max = 50)
    @EnglishLanguage(withPunctuations = false)
    private String name;

    @EnglishLanguage
    private String description;

    @Positive
    private Double price;

    @Positive
    private Integer duration;

    private List<@NotNull @Min(1) Long> tags;

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
            existing.setTags(tags.stream()
                    .map(id -> {
                        Tag tag = new Tag();
                        tag.setId(id);
                        return tag;
                    }).collect(Collectors.toList()));
        }
    }
}
