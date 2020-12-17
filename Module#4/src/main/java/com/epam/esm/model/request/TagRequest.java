package com.epam.esm.model.request;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TagRequest {
    @NotNull
    @Size(min = 3, max = 50)
    @EnglishLanguage(withSpecSymbols = false, withNumbers = false)
    private String name;

    public static Tag toTag(TagRequest tagRequest){
        Tag tag = new Tag();
        tag.setName(tagRequest.getName());
        return tag;
    }
}
