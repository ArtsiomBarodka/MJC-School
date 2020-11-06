package com.epam.esm.entity;

import com.epam.esm.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Gift certificate.
 */
@Data
public class GiftCertificate {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @EnglishLanguage(withPunctuations = false)
    private String name;

    @NotNull
    @EnglishLanguage
    private String description;

    @NotNull
    @Positive
    private Double price;

    private String createDate;

    private String lastUpdateDate;

    @NotNull
    @Positive
    private Integer duration;

    private List<Tag> tags;

    /**
     * Instantiates a new Gift certificate.
     */
    public GiftCertificate() {
        tags = new ArrayList<>();
    }


    /**
     * Add tag.
     *
     * @param tag the tag
     */
    public void addTag(Tag tag) {
        if (tag != null) {
            tags.add(tag);
        }
    }

    /**
     * Delete tag.
     *
     * @param tag the tag
     */
    public void deleteTag(Tag tag) {
        if (tag != null) {
            tags.removeIf(t -> t.getId().equals(tag.getId()));
        }
    }

}
