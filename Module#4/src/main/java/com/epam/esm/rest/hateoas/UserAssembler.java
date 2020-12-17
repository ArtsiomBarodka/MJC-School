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
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserView> {
    private static final String CREATE_LINK_RELATION = "createUser";
    private static final String UPDATE_LINK_RELATION = "updateUser";
    private static final String GET_ALL_LINK_RELATION = "getAllUsers";

    public UserAssembler() {
        super(UserController.class, UserView.class);
    }

    @SneakyThrows
    @Override
    public UserView toModel(User entity) {
        UserView model = UserView.fromUserToUserView(entity);
        Link selfLink = linkTo(UserController.class).slash(model.getId()).withSelfRel();
        Link updateLink = linkTo(UserController.class).slash(model.getId()).withRel(UPDATE_LINK_RELATION);
        Link getAllLink = linkTo(UserController.class).withRel(GET_ALL_LINK_RELATION);
        model.add(selfLink);
        model.add(updateLink);
        model.add(getAllLink);
        model.setOrders(toOrderModel(entity.getOrders()));
        return model;
    }

    public List<Link> getLinksToCollectionModel() {
        List<Link> result = new ArrayList<>();
        Link createLink = linkTo(UserController.class).withRel(CREATE_LINK_RELATION);
        result.add(createLink);
        return result;
    }

    private List<OrderView> toOrderModel(List<Order> orders) {
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream()
                .map(order -> {
                    OrderView model = OrderView.fromOrderToOrderView(order);
                    try {
                        Link link = linkTo(methodOn(OrderController.class).getOrderById(model.getId())).withSelfRel();
                        if (!model.hasLink(link.getRel())) {
                            model.add(link);
                            model.setGiftCertificates(toGiftCertificateModel(order.getGiftCertificates()));
                        }
                        return model;
                    } catch (ResourceNotFoundException e) {
                        //do nothing
                    }
                    return model;
                })
                .collect(Collectors.toList());
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
