package com.epam.esm.rest.hateoas;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.rest.controller.GiftCertificateController;
import com.epam.esm.rest.controller.TagController;
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
public class GiftCertificateAssembler extends RepresentationModelAssemblerSupport<GiftCertificate, GiftCertificate> {

    private static final String GET_GIFT_CERTIFICATES_BY_TAG_NAMES = "tags";

    private static final String CREATE_LINK_RELATION = "createGiftCertificate";
    private static final String UPDATE_LINK_RELATION = "updateGiftCertificate";
    private static final String DELETE_LINK_RELATION = "deleteGiftCertificate";
    private static final String GET_ALL_LINK_RELATION = "getAllGiftCertificates";
    private static final String GET_BY_TAG_NAMES_LINK_RELATION = "getGiftCertificatesByTagNames";

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

    public List<Link> getLinksToCollectionModel() {
        List<Link> result = new ArrayList<>();
        Link getByTagNamesLink = linkTo(GiftCertificateController.class).slash(GET_GIFT_CERTIFICATES_BY_TAG_NAMES).withRel(GET_BY_TAG_NAMES_LINK_RELATION);
        Link createLink = linkTo(GiftCertificateController.class).withRel(CREATE_LINK_RELATION);
        result.add(getByTagNamesLink);
        result.add(createLink);
        return result;
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
