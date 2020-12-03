package com.epam.esm.component.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.ResourceNotFoundException;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Tag assembler.
 */
@Component
public class TagAssembler extends RepresentationModelAssemblerSupport<Tag, Tag> {
    private static final String GET_THE_MOST_WIDELY_USED_TAG = "top/user";
    private static final String GET_TAGS_BY_GIFT_CERTIFICATE_ID = "certificate";

    private static final String CREATE_LINK_RELATION = "createTag";
    private static final String UPDATE_LINK_RELATION = "updateTag";
    private static final String DELETE_LINK_RELATION = "deleteTag";
    private static final String GET_ALL_LINK_RELATION = "getAllTags";
    private static final String GET_THE_MOST_WIDELY_USED_LINK_RELATION = "getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders";
    private static final String GET_BY_GIFT_CERTIFICATE_ID_LINK_RELATION = "getTagsByGiftCertificateId";

    /**
     * Instantiates a new Tag assembler.
     */
    public TagAssembler() {
        super(TagController.class, Tag.class);
    }

    @SneakyThrows
    @Override
    public Tag toModel(Tag entity) {
        Link selfLink = linkTo(methodOn(TagController.class).getTagById(entity.getId())).withSelfRel();
        Link deleteLink = linkTo(methodOn(TagController.class).deleteTag(entity.getId())).withRel(DELETE_LINK_RELATION);
        Link updateLink = linkTo(TagController.class).slash(entity.getId()).withRel(UPDATE_LINK_RELATION);
        Link getAllLink = linkTo(TagController.class).withRel(GET_ALL_LINK_RELATION);
        entity.add(selfLink);
        entity.add(updateLink);
        entity.add(deleteLink);
        entity.add(getAllLink);
        entity.setGiftCertificates(toGiftCertificateModel(entity.getGiftCertificates()));
        return entity;
    }

    @SneakyThrows
    @Override
    public CollectionModel<Tag> toCollectionModel(Iterable<? extends Tag> entities) {
        CollectionModel<Tag> tagModels = super.toCollectionModel(entities);
        Link createLink = linkTo(TagController.class).withRel(CREATE_LINK_RELATION);
        Link getTheMostWidelyUsed = linkTo(TagController.class).slash(GET_THE_MOST_WIDELY_USED_TAG).withRel(GET_THE_MOST_WIDELY_USED_LINK_RELATION);
        Link getTagsByGiftCertificateId = linkTo(TagController.class).slash(GET_TAGS_BY_GIFT_CERTIFICATE_ID).withRel(GET_BY_GIFT_CERTIFICATE_ID_LINK_RELATION);
        tagModels.add(getSelfLink());
        tagModels.add(createLink);
        tagModels.add(getTheMostWidelyUsed);
        tagModels.add(getTagsByGiftCertificateId);
        return tagModels;
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
                        }
                        return giftCertificate;
                    } catch (ResourceNotFoundException e) {
                        //do nothing
                    }
                    return giftCertificate;
                })
                .collect(Collectors.toList());
    }

    private Link getSelfLink() {
        String self = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
        return Link.of(self);
    }
}
