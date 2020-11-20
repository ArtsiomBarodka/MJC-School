package com.epam.esm.entity;

import com.epam.esm.validation.annotation.EnglishLanguage;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders;

    public User(){
        orders = new ArrayList<>();
    }
}
