package com.epam.esm.model.request;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Order request.
 */
@Data
public class OrderRequest {
    @NotNull
    @Positive
    private Long userId;

    @Size(min = 1)
    private List<@NotNull @Positive Long> giftCertificates = new ArrayList<>();

    /**
     * To order order.
     *
     * @param orderRequest the order request
     * @return the order
     */
    public static Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        User user = new User();
        user.setId(orderRequest.getUserId());
        order.setUser(user);
        order.setGiftCertificates(orderRequest.getGiftCertificates()
                .stream()
                .map(id->{
                    GiftCertificate c = new GiftCertificate();
                    c.setId(id);
                    return c;
                }).collect(Collectors.toList()));
        return order;
    }

}
