package com.epam.esm.model.view;

import com.epam.esm.model.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.List;

@Data
@Relation(collectionRelation = "orders")
@EqualsAndHashCode(callSuper = false)
public class OrderView extends RepresentationModel<OrderView> {
    private Long id;

    private Date createdDate;

    private Double sumPrice;

    @JsonIgnoreProperties("orders")
    private UserView user;

    private List<GiftCertificateView> giftCertificates;

    public static OrderView fromOrderToOrderView(Order order) {
        OrderView orderView = new OrderView();
        orderView.setId(order.getId());
        orderView.setCreatedDate(order.getCreatedDate());
        orderView.setSumPrice(order.getSumPrice());
        return orderView;
    }

}
