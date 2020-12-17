package com.epam.esm.rest.hateoas;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.model.view.GiftCertificateView;
import com.epam.esm.model.view.OrderView;
import com.epam.esm.model.view.TagView;
import com.epam.esm.model.view.UserView;
import com.epam.esm.rest.controller.GiftCertificateController;
import com.epam.esm.rest.controller.OrderController;
import com.epam.esm.rest.controller.TagController;
import com.epam.esm.rest.controller.UserController;
import lombok.SneakyThrows;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler extends RepresentationModelAssemblerSupport<Order, OrderView> {
    private static final String GET_ORDERS_BY_USER_ID = "user";

    private static final String CREATE_LINK_RELATION = "createOrder";
    private static final String DELETE_LINK_RELATION = "deleteOrder";
    private static final String GET_ORDERS_BY_USER_ID_LINK_RELATION = "getOrdersByUserId";

    public OrderAssembler() {
        super(OrderController.class, OrderView.class);
    }

    @SneakyThrows
    @Override
    public OrderView toModel(Order entity) {
        OrderView model = OrderView.fromOrderToOrderView(entity);
        Link selfLink = linkTo(methodOn(OrderController.class).getOrderById(model.getId())).withSelfRel();
        Link deleteLink = linkTo(methodOn(OrderController.class).getOrderById(model.getId())).withRel(DELETE_LINK_RELATION);
        model.add(selfLink);
        model.add(deleteLink);
        model.setUser(toUserModel(entity.getUser()));
        model.setGiftCertificates(toGiftCertificateModel(entity.getGiftCertificates()));
        return model;
    }

    public List<Link> getLinksToCollectionModel() {
        List<Link> result = new ArrayList<>();
        Link createLink = linkTo(OrderController.class).withRel(CREATE_LINK_RELATION);
        Link getOrdersByUserId = linkTo(OrderController.class).slash(GET_ORDERS_BY_USER_ID).withRel(GET_ORDERS_BY_USER_ID_LINK_RELATION);
        result.add(createLink);
        result.add(getOrdersByUserId);
        return result;
    }

    private UserView toUserModel(User user) {
        UserView model = UserView.fromUserToUserView(user);
        Link link = linkTo(UserController.class).slash(model.getId()).withSelfRel();
        if (!model.hasLink(link.getRel())) {
            model.add(link);
        }
        return model;
    }

    private List<GiftCertificateView> toGiftCertificateModel(List<GiftCertificate> giftCertificates) {
        if (giftCertificates.isEmpty()) {
            return Collections.emptyList();
        }

        return giftCertificates.stream()
                .map(giftCertificate -> {
                    GiftCertificateView model = GiftCertificateView.fromGiftCertificateToGiftCertificateView(giftCertificate);
                    try {
                        Link link = linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(model.getId())).withSelfRel();
                        if (!model.hasLink(link.getRel())) {
                            model.add(link);
                            model.setTags(toTagModel(giftCertificate.getTags()));
                        }
                        return model;
                    } catch (ResourceNotFoundException e) {
                        //do nothing
                    }
                    return model;
                })
                .collect(Collectors.toList());
    }

    private List<TagView> toTagModel(List<Tag> tags) {
        if (tags.isEmpty()) {
            return Collections.emptyList();
        }

        return tags.stream()
                .map(tag -> {
                    TagView model = TagView.fromTagToTagView(tag);
                    try {
                        Link link = linkTo(methodOn(TagController.class).getTagById(model.getId())).withSelfRel();
                        if (!model.hasLink(link.getRel())) {
                            model.add(link);
                        }
                        return model;
                    } catch (ResourceNotFoundException e) {
                        //do nothing
                    }
                    return model;
                })
                .collect(Collectors.toList());
    }
}
