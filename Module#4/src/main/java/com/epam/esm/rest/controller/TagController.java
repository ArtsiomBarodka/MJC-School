package com.epam.esm.rest.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.model.patch.PatchTag;
import com.epam.esm.rest.component.assembler.TagAssembler;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@Controller
@RequestMapping("api/v1/tags")
@Validated
public class TagController {
    private final TagService tagService;
    private final TagAssembler assembler;

    @Autowired
    public TagController(TagService tagService, TagAssembler assembler) {
        this.tagService = tagService;
        this.assembler = assembler;
    }

    @GetMapping("/top/user")
    public ResponseEntity<Tag> geTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders()
            throws ResourceNotFoundException {

        Tag tag = tagService.getTheMostWidelyUsedTagOfUsersFromTheHighestCostOfAllOrders();
        return ResponseEntity.ok(assembler.toModel(tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        Tag tag = tagService.getById(id);
        return ResponseEntity.ok(assembler.toModel(tag));
    }

    @GetMapping
    public ResponseEntity<PagedModel<Tag>> getListTags(Pageable pageable,
                                                       PagedResourcesAssembler<Tag> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<Tag> tags = tagService.getAll(pageable);
        PagedModel<Tag> pagedModel = pagedResourcesAssembler.toModel(tags, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/giftCertificates")
    public ResponseEntity<CollectionModel<Tag>> getListTagsByGiftCertificatesById(@RequestParam @NotNull @Min(1) Long id,
                                                                                  Pageable pageable,
                                                                                  PagedResourcesAssembler<Tag> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<Tag> tags = tagService.getListByGiftCertificateId(id, pageable);
        PagedModel<Tag> pagedModel = pagedResourcesAssembler.toModel(tags, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

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

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartOfTag(@PathVariable("id") @Min(1) Long id,
                                                  @RequestBody @Valid PatchTag patchTag)
            throws ResourceNotFoundException, BadParametersException {

        Tag existingTag = tagService.getById(id);
        patchTag.mergeToEntity(existingTag);
        Tag updatedTag = tagService.update(existingTag, id);
        return ResponseEntity.ok(assembler.toModel(updatedTag));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
