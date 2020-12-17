package com.epam.esm.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "certificate")
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    @Column(name = "create_date", insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "last_update_date", insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    private Integer duration;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    public GiftCertificate() {
        tags = new ArrayList<>();
    }

}
