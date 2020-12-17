package com.epam.esm.model.request;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @NotNull
    @Size(min = 3, max = 25)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false)
    private String username;

    @NotNull
    @Size(min = 3, max = 25)
    @EnglishLanguage
    private String password;

    @NotNull
    @Size(min = 3, max = 45)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false, withNumbers = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 45)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false, withNumbers = false)
    private String lastName;

    public static User toUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        return user;
    }
}
