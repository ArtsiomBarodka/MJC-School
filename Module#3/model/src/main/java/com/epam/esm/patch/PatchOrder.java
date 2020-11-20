package com.epam.esm.patch;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class PatchOrder implements PatchOperation<Order> {
    private User user;
    private List<GiftCertificate> giftCertificates;

    @Override
    public void mergeToEntity(Order existing) {
        if (user != null) {
            existing.setUser(user);
        }
        if (giftCertificates != null) {
            existing.setGiftCertificates(giftCertificates);
        }
    }
}
