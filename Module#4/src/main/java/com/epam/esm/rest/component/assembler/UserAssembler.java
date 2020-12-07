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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, User> {
    private static final String CREATE_LINK_RELATION = "createUser";
    private static final String UPDATE_LINK_RELATION = "updateUser";
    private static final String GET_ALL_LINK_RELATION = "getAllUsers";

    public UserAssembler() {
        super(UserController.class, User.class);
    }

    @SneakyThrows
    @Override
    public User toModel(User entity) {
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel();
        Link updateLink = linkTo(UserController.class).slash(entity.getId()).withRel(UPDATE_LINK_RELATION);
        Link getAllLink = linkTo(UserController.class).withRel(GET_ALL_LINK_RELATION);
        entity.add(selfLink);
        entity.add(updateLink);
        entity.add(getAllLink);
        entity.setOrders(toOrderModel(entity.getOrders()));
        return entity;
    }

    public List<Link> getLinksToCollectionModel() {
        List<Link> result = new ArrayList<>();
        Link createLink = linkTo(UserController.class).withRel(CREATE_LINK_RELATION);
        result.add(createLink);
        return result;
    }

    private List<Order> toOrderModel(List<Order> orders) {
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream()
                .map(order -> {
                    try {
                        Link link = linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel();
                        if (!order.hasLink(link.getRel())) {
                            order.add(link);
                            order.setGiftCertificates(toGiftCertificateModel(order.getGiftCertificates()));
                        }
                        return order;
                    } catch (ResourceNotFoundException e) {
                        //do nothing
                    }
                    return order;
                })
                .collect(Collectors.toList());
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
