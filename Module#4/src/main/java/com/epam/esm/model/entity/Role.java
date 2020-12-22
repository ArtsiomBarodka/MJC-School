package com.epam.esm.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * The type Role.
 */
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
