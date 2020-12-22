package com.epam.esm.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Order.
 */
@Data
@Entity
@Table(name = "user_order")
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
    @JoinColumn(name = "fk_user_id")
    private User user;

    @ToString.Exclude
    @JoinTable(name = "certificate_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private List<GiftCertificate> giftCertificates;

    /**
     * Instantiates a new Order.
     */
    public Order() {
        giftCertificates = new ArrayList<>();
    }
}
