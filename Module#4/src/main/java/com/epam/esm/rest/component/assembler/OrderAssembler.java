package com.epam.esm.rest.component.assembler;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Order assembler.
 */
@Component
public class OrderAssembler extends RepresentationModelAssemblerSupport<Order, Order> {
    private static final String GET_ORDERS_BY_USER_ID = "user";

    private static final String CREATE_LINK_RELATION = "createOrder";
    private static final String DELETE_LINK_RELATION = "deleteOrder";
    private static final String GET_ORDERS_BY_USER_ID_LINK_RELATION = "getOrdersByUserId";

    /**
     * Instantiates a new Order assembler.
     */
    public OrderAssembler() {
        super(OrderController.class, Order.class);
    }

    @SneakyThrows
    @Override
    public Order toModel(Order entity) {
        Link selfLink = linkTo(methodOn(OrderController.class).getOrderById(entity.getId())).withSelfRel();
        Link deleteLink = linkTo(methodOn(OrderController.class).getOrderById(entity.getId())).withRel(DELETE_LINK_RELATION);
        entity.add(selfLink);
        entity.add(deleteLink);
        entity.setUser(toUserModel(entity.getUser()));
        entity.setGiftCertificates(toGiftCertificateModel(entity.getGiftCertificates()));
        return entity;
    }

    /**
     * Gets links to collection model.
     *
     * @param entities the entities
     * @return the links to collection model
     */
    public List<Link> getLinksToCollectionModel(Iterable<? extends Order> entities) {
        List<Link> result = new ArrayList<>();
        Link createLink = linkTo(OrderController.class).withRel(CREATE_LINK_RELATION);
        Link getOrdersByUserId = linkTo(OrderController.class).slash(GET_ORDERS_BY_USER_ID).withRel(GET_ORDERS_BY_USER_ID_LINK_RELATION);
        result.add(createLink);
        result.add(getOrdersByUserId);
        return result;
    }

    private User toUserModel(User user) {
        return Optional.ofNullable(user).map((u) -> {
            try {
                Link link = linkTo(methodOn(UserController.class).getUserById(u.getId())).withSelfRel();
                if (!u.hasLink(link.getRel())) {
                    u.add(link);
                }
                return u;
            } catch (ResourceNotFoundException e) {
                //do nothing
            }
            return u;
        }).orElse(user);
    }

    private List<GiftCertificate> toGiftCertificateModel(List<GiftCertificate> giftCertificates) {
        if (giftCertificates.isEmpty()) {
            return Collections.emptyList();
        }

        return giftCertificates.stream()
                .map(giftCertificate -> {
                    try {
                        Link link = linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(giftCertificate.getId())).withSelfRel();
                        if (!giftCertificate.hasLink(link.getRel())) {
                            giftCertificate.add(link);
                            giftCertificate.setTags(toTagModel(giftCertificate.getTags()));
                        }
                        return giftCertificate;
                    } catch (ResourceNotFoundException e) {
                        //do nothing
                    }
                    return giftCertificate;
                })
                .collect(Collectors.toList());
    }

    private List<Tag> toTagModel(List<Tag> tags) {
        if (tags.isEmpty()) {
            return Collections.emptyList();
        }

        return tags.stream()
                .map(tag -> {
                    try {
                        Link link = linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel();
                        if (!tag.hasLink(link.getRel())) {
                            tag.add(link);
                        }
                        return tag;
                    } catch (ResourceNotFoundException e) {
                        //do nothing
                    }
                    return tag;
                })
                .collect(Collectors.toList());
    }
}
