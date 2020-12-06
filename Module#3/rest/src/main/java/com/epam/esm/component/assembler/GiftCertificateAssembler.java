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
 * The type Gift certificate assembler.
 */
@Component
public class GiftCertificateAssembler extends RepresentationModelAssemblerSupport<GiftCertificate, GiftCertificate> {

    private static final String GET_GIFT_CERTIFICATES_BY_TAG_NAMES = "tags";

    private static final String CREATE_LINK_RELATION = "createGiftCertificate";
    private static final String UPDATE_LINK_RELATION = "updateGiftCertificate";
    private static final String DELETE_LINK_RELATION = "deleteGiftCertificate";
    private static final String GET_ALL_LINK_RELATION = "getAllGiftCertificates";
    private static final String GET_BY_TAG_NAMES_LINK_RELATION = "getGiftCertificatesByTagNames";

    /**
     * Instantiates a new Gift certificate assembler.
     */
    public GiftCertificateAssembler() {
        super(GiftCertificateController.class, GiftCertificate.class);
    }


    @SneakyThrows
    @Override
    public GiftCertificate toModel(GiftCertificate entity) {
        Link selfLink = linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(entity.getId())).withSelfRel();
        Link deleteLink = linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(entity.getId())).withRel(DELETE_LINK_RELATION);
        Link updateLink = linkTo(GiftCertificateController.class).slash(entity.getId()).withRel(UPDATE_LINK_RELATION);
        Link getAllLink = linkTo(GiftCertificateController.class).withRel(GET_ALL_LINK_RELATION);
        entity.add(selfLink);
        entity.add(updateLink);
        entity.add(deleteLink);
        entity.add(getAllLink);
        entity.setTags(toTagModel(entity.getTags()));
        return entity;
    }

    @Override
    public CollectionModel<GiftCertificate> toCollectionModel(Iterable<? extends GiftCertificate> entities) {
        CollectionModel<GiftCertificate> giftCertificateModels = super.toCollectionModel(entities);
        Link getByTagNamesLink = linkTo(GiftCertificateController.class).slash(GET_GIFT_CERTIFICATES_BY_TAG_NAMES).withRel(GET_BY_TAG_NAMES_LINK_RELATION);
        Link createLink = linkTo(GiftCertificateController.class).withRel(CREATE_LINK_RELATION);
        giftCertificateModels.add(getSelfLink());
        giftCertificateModels.add(getByTagNamesLink);
        giftCertificateModels.add(createLink);
        return giftCertificateModels;
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

    private Link getSelfLink() {
        String self = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
        return Link.of(self);
    }
}
