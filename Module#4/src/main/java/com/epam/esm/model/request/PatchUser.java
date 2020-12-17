package com.epam.esm.model.request;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PatchUser implements PatchOperation<User> {

    @Size(min = 3, max = 45)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false, withNumbers = false)
    private String firstName;

    @Size(min = 3, max = 45)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false, withNumbers = false)
    private String lastName;

    @Override
    public void mergeToEntity(User existing) {
        if (firstName != null) {
            existing.setFirstName(firstName);
        }
        if (lastName != null) {
            existing.setLastName(lastName);
        }
    }
}
