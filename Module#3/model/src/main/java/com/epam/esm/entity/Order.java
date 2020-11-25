package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user_order")
public class Order extends RepresentationModel<Order> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "create_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "price")
    private Double sumPrice;

    @JsonIgnoreProperties("orders")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id")
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
