package com.epam.esm.entity;

import com.epam.esm.validation.annotation.EnglishLanguage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false)
    private String name;

    @JsonIgnoreProperties("user")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Order> orders;

    public User() {
        orders = new ArrayList<>();
    }
}
