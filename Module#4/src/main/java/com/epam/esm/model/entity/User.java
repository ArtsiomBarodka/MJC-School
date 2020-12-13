package com.epam.esm.model.entity;

import com.epam.esm.model.validation.annotation.EnglishLanguage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roles;

    @ToString.Exclude
    @JsonIgnoreProperties("user")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Order> orders;

    public User() {
        orders = new ArrayList<>();
    }
}
