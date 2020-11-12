package com.epam.esm.entity;

import com.epam.esm.validation.annotation.EnglishLanguage;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Gift certificate.
 */
@Data
@Entity
@Table(name = "certificate")
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @EnglishLanguage(withPunctuations = false)
    @Column
    private String name;

    @NotNull
    @EnglishLanguage
    @Column
    private String description;

    @NotNull
    @Positive
    @Column
    private Double price;

    @Column(name = "create_date", insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "last_update_date", insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    @NotNull
    @Positive
    @Column
    private Integer duration;

    @JsonIgnoreProperties("giftCertificates")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    /**
     * Instantiates a new Gift certificate.
     */
    public GiftCertificate() {
        tags = new ArrayList<>();
    }

}
