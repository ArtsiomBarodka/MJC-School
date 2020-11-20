package com.epam.esm.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "create_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "price")
    private Double sumPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @JoinTable(name = "certificate_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    private List<GiftCertificate> giftCertificates;

    public Order() {
        giftCertificates = new ArrayList<>();
    }
}
