package com.epam.esm.rest.hateoas;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.model.view.GiftCertificateView;
import com.epam.esm.model.view.TagView;
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

/**
 * The type Gift certificate assembler.
 */
@Component
public class GiftCertificateAssembler extends RepresentationModelAssemblerSupport<GiftCertificate, GiftCertificateView> {

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
        super(GiftCertificateController.class, GiftCertificateView.class);
    }


    @SneakyThrows
    @Override
    public GiftCertificateView toModel(GiftCertificate entity) {
        GiftCertificateView model = GiftCertificateView.fromGiftCertificateToGiftCertificateView(entity);
        Link selfLink = linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(model.getId())).withSelfRel();
        Link deleteLink = linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(model.getId())).withRel(DELETE_LINK_RELATION);
        Link updateLink = linkTo(GiftCertificateController.class).slash(model.getId()).withRel(UPDATE_LINK_RELATION);
        Link getAllLink = linkTo(GiftCertificateController.class).withRel(GET_ALL_LINK_RELATION);
        model.add(selfLink);
        model.add(updateLink);
        model.add(deleteLink);
        model.add(getAllLink);
        model.setTags(toTagModel(entity.getTags()));
        return model;
    }

    /**
     * Gets links to collection model.
     *
     * @return the links to collection model
     */
    public List<Link> getLinksToCollectionModel() {
        List<Link> result = new ArrayList<>();
        Link getByTagNamesLink = linkTo(GiftCertificateController.class).slash(GET_GIFT_CERTIFICATES_BY_TAG_NAMES).withRel(GET_BY_TAG_NAMES_LINK_RELATION);
        Link createLink = linkTo(GiftCertificateController.class).withRel(CREATE_LINK_RELATION);
        result.add(getByTagNamesLink);
        result.add(createLink);
        return result;
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
