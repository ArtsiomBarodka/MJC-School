package com.epam.esm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdDate;

    @Transient
    private Double sumPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id");
    private User user;

    @JoinTable(name = "order_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private List<GiftCertificate> giftCertificates;

    public void setGiftCertificates(List<GiftCertificate> giftCertificates){
        this.giftCertificates = giftCertificates;
        giftCertificates.forEach((g)-> sumPrice+=g.getPrice());
    }
}
