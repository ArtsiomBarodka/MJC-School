package com.epam.esm.model.request;

import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Sign in request.
 */
@Data
public class SignInRequest {
    @NotNull
    @Size(min = 3, max = 25)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false)
    private String username;

    @NotNull
    @Size(min = 3, max = 25)
    @EnglishLanguage
    private String password;
}
