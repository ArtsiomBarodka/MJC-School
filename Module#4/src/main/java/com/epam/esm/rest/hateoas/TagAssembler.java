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
 * The type Tag assembler.
 */
@Component
public class TagAssembler extends RepresentationModelAssemblerSupport<Tag, TagView> {
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
        super(TagController.class, TagView.class);
    }

    @SneakyThrows
    @Override
    public TagView toModel(Tag entity) {
        TagView model = TagView.fromTagToTagView(entity);
        Link selfLink = linkTo(methodOn(TagController.class).getTagById(model.getId())).withSelfRel();
        Link deleteLink = linkTo(methodOn(TagController.class).deleteTag(model.getId())).withRel(DELETE_LINK_RELATION);
        Link updateLink = linkTo(TagController.class).slash(model.getId()).withRel(UPDATE_LINK_RELATION);
        Link getAllLink = linkTo(TagController.class).withRel(GET_ALL_LINK_RELATION);
        model.add(selfLink);
        model.add(updateLink);
        model.add(deleteLink);
        model.add(getAllLink);
        model.setGiftCertificates(toGiftCertificateModel(entity.getGiftCertificates()));
        return model;
    }

    /**
     * Gets links to collection model.
     *
     * @return the links to collection model
     */
    public List<Link> getLinksToCollectionModel() {
        List<Link> result = new ArrayList<>();
        Link createLink = linkTo(TagController.class).withRel(CREATE_LINK_RELATION);
        Link getTheMostWidelyUsed = linkTo(TagController.class).slash(GET_THE_MOST_WIDELY_USED_TAG).withRel(GET_THE_MOST_WIDELY_USED_LINK_RELATION);
        Link getTagsByGiftCertificateId = linkTo(TagController.class).slash(GET_TAGS_BY_GIFT_CERTIFICATE_ID).withRel(GET_BY_GIFT_CERTIFICATE_ID_LINK_RELATION);
        result.add(createLink);
        result.add(getTheMostWidelyUsed);
        result.add(getTagsByGiftCertificateId);
        return result;
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
