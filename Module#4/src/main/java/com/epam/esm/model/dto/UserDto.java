package com.epam.esm.model.dto;

import com.epam.esm.model.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @NotNull
    @Size(min = 3, max = 50)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false)
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    @EnglishLanguage
    private String password;

    @NotNull
    @Size(min = 3, max = 45)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false, withNumbers = false)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 45)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false, withNumbers = false)
    @Column(name = "last_name")
    private String lastName;


}
