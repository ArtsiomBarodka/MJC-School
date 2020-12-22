package com.epam.esm.rest.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.model.request.PatchTag;
import com.epam.esm.model.request.TagRequest;
import com.epam.esm.model.view.TagView;
import com.epam.esm.rest.hateoas.TagAssembler;
import com.epam.esm.security.annotation.AdminRole;
import com.epam.esm.security.annotation.AllRoles;
import com.epam.esm.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Tag controller.
 */
@AllArgsConstructor
@Controller
@RequestMapping("api/v1/tags")
@Validated
public class TagController {
    private final TagService tagService;
    private final TagAssembler assembler;

    /**
     * Ge the most widely used tag of user with the highest cost of all orders response entity.
     *
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @AllRoles
    @GetMapping("/top/user")
    public ResponseEntity<TagView> geTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders()
            throws ResourceNotFoundException {

        Tag tag = tagService.getTheMostWidelyUsedTagOfUsersFromTheHighestCostOfAllOrders();
        return ResponseEntity.ok(assembler.toModel(tag));
    }

    /**
     * Gets tag by id.
     *
     * @param id the id
     * @return the tag by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @AllRoles
    @GetMapping("/{id}")
    public ResponseEntity<TagView> getTagById(@PathVariable("id") @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        Tag tag = tagService.getById(id);
        return ResponseEntity.ok(assembler.toModel(tag));
    }

    /**
     * Gets list tags.
     *
     * @param pageable                the pageable
     * @param pagedResourcesAssembler the paged resources assembler
     * @return the list tags
     * @throws ResourceNotFoundException the resource not found exception
     */
    @AllRoles
    @GetMapping
    public ResponseEntity<PagedModel<TagView>> getListTags(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
                                                           PagedResourcesAssembler<Tag> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<Tag> tags = tagService.getAll(pageable);
        PagedModel<TagView> pagedModel = pagedResourcesAssembler.toModel(tags, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Gets list tags by gift certificates by id.
     *
     * @param id                      the id
     * @param pageable                the pageable
     * @param pagedResourcesAssembler the paged resources assembler
     * @return the list tags by gift certificates by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @AllRoles
    @GetMapping("/giftCertificates")
    public ResponseEntity<CollectionModel<TagView>> getListTagsByGiftCertificatesById(@RequestParam @NotNull @Min(1) Long id,
                                                                                      @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
                                                                                      PagedResourcesAssembler<Tag> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<Tag> tags = tagService.getListByGiftCertificateId(id, pageable);
        PagedModel<TagView> pagedModel = pagedResourcesAssembler.toModel(tags, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Create tag response entity.
     *
     * @param tagRequest           the tag request
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @AdminRole
    @PostMapping
    public ResponseEntity<Object> createTag(@RequestBody @Valid TagRequest tagRequest,
                                            UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/tags/{id}")
                        .buildAndExpand(tagService.create(TagRequest.toTag(tagRequest)))
                        .toUri())
                .build();
    }

    /**
     * Update or create tag response entity.
     *
     * @param id                   the id
     * @param tagRequest           the tag request
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @AdminRole
    @PutMapping("/{id}")
    public ResponseEntity<TagView> updateOrCreateTag(@PathVariable("id") @Min(1) Long id,
                                                     @RequestBody @Valid TagRequest tagRequest,
                                                     UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException {

        Tag tag = TagRequest.toTag(tagRequest);
        try {
            Tag updatedTag = tagService.update(tag, id);
            return ResponseEntity.ok(assembler.toModel(updatedTag));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.created(
                    uriComponentsBuilder
                            .path("/api/v1/tags/{id}")
                            .buildAndExpand(tagService.create(tag))
                            .toUri())
                    .build();
        }
    }

    /**
     * Update part of tag response entity.
     *
     * @param id       the id
     * @param patchTag the patch tag
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @AdminRole
    @PatchMapping("/{id}")
    public ResponseEntity<TagView> updatePartOfTag(@PathVariable("id") @Min(1) Long id,
                                                   @RequestBody @Valid PatchTag patchTag)
            throws ResourceNotFoundException, BadParametersException {

        Tag existingTag = tagService.getById(id);
        patchTag.mergeToEntity(existingTag);
        Tag updatedTag = tagService.update(existingTag, id);
        return ResponseEntity.ok(assembler.toModel(updatedTag));
    }


    /**
     * Delete tag response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @AdminRole
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
