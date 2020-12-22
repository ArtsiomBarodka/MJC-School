package com.epam.esm.model.request;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * The type Patch tag.
 */
@Data
public class PatchTag implements PatchOperation<Tag> {
    @Size(min = 3, max = 50)
    @EnglishLanguage(withPunctuations = false)
    private String name;

    @Override
    public void mergeToEntity(Tag existing) {
        if (name != null) {
            existing.setName(name);
        }
    }
}
