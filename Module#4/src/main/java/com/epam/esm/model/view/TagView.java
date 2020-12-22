package com.epam.esm.model.view;

import com.epam.esm.model.entity.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

/**
 * The type Tag view.
 */
@Data
@Relation(collectionRelation = "tags")
@EqualsAndHashCode(callSuper = false)
public class TagView extends RepresentationModel<TagView> {
    private Long id;

    private String name;

    private List<GiftCertificateView> giftCertificates;

    /**
     * From tag to tag view tag view.
     *
     * @param tag the tag
     * @return the tag view
     */
    public static TagView fromTagToTagView(Tag tag) {
        TagView tagView = new TagView();
        tagView.setId(tag.getId());
        tagView.setName(tag.getName());
        return tagView;
    }
}
