package com.epam.esm.controller;

import com.epam.esm.component.assembler.TagAssembler;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.patch.PatchTag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The type Tag controller.
 */
@Controller
@RequestMapping("api/v1/tags")
@Validated
public class TagController {
    private final TagService tagService;
    private final TagAssembler assembler;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService the tag service
     * @param assembler  the assembler
     */
    @Autowired
    public TagController(TagService tagService, TagAssembler assembler) {
        this.tagService = tagService;
        this.assembler = assembler;
    }

    /**
     * Ge the most widely used tag of user with the highest cost of all orders response entity.
     *
     * @param userId the user id
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/top/user")
    public ResponseEntity<Tag> geTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders(@RequestParam("id") @NotNull @Min(1) Long userId)
            throws ResourceNotFoundException {

        Tag tag = tagService.getTheMostWidelyUsedTagOfUserFromTheHighestCostOfAllOrders(userId);
        return ResponseEntity.ok(assembler.toModel(tag));
    }

    /**
     * Gets tag by id.
     *
     * @param id the id
     * @return the tag by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        Tag tag = tagService.getById(id);
        return ResponseEntity.ok(assembler.toModel(tag));
    }

    /**
     * Gets list tags.
     *
     * @param sort the sort
     * @param page the page
     * @param size the size
     * @return the list tags
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping
    public ResponseEntity<CollectionModel<Tag>> getListTags(@RequestParam(required = false) String sort,
                                                            @RequestParam(required = false) @Min(0) Integer page,
                                                            @RequestParam(required = false) @Min(1) Integer size)
            throws ResourceNotFoundException {

        Page pageable = new Page(page, size);
        List<Tag> tags = tagService.getAll(pageable, SortMode.of(sort));
        return ResponseEntity.ok(assembler.toCollectionModel(tags));
    }

    /**
     * Gets list tags by gift certificates by id.
     *
     * @param id   the id
     * @param sort the sort
     * @param page the page
     * @param size the size
     * @return the list tags by gift certificates by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/giftCertificates")
    public ResponseEntity<CollectionModel<Tag>> getListTagsByGiftCertificatesById(@RequestParam @NotNull @Min(1) Long id,
                                                                                  @RequestParam(required = false) String sort,
                                                                                  @RequestParam(required = false) @Min(0) Integer page,
                                                                                  @RequestParam(required = false) @Min(1) Integer size)
            throws ResourceNotFoundException {

        Page pageable = new Page(page, size);
        List<Tag> tags = tagService.getListByGiftCertificateId(id, pageable, SortMode.of(sort));
        return ResponseEntity.ok(assembler.toCollectionModel(tags));
    }

    /**
     * Create tag response entity.
     *
     * @param tag                  the tag
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @PostMapping
    public ResponseEntity<Object> createTag(@RequestBody @Valid Tag tag,
                                            UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/tags/{id}")
                        .buildAndExpand(tagService.create(tag))
                        .toUri())
                .build();
    }

    /**
     * Update or create tag response entity.
     *
     * @param id                   the id
     * @param tag                  the tag
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateOrCreateTag(@PathVariable("id") @Min(1) Long id,
                                                 @RequestBody @Valid Tag tag,
                                                 UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException {

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
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartOfTag(@PathVariable("id") @Min(1) Long id,
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
