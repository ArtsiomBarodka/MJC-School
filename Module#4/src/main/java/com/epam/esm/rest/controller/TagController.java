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

@AllArgsConstructor
@Controller
@RequestMapping("api/v1/tags")
@Validated
public class TagController {
    private final TagService tagService;
    private final TagAssembler assembler;

    @AllRoles
    @GetMapping("/top/user")
    public ResponseEntity<TagView> geTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders()
            throws ResourceNotFoundException {

        Tag tag = tagService.getTheMostWidelyUsedTagOfUsersFromTheHighestCostOfAllOrders();
        return ResponseEntity.ok(assembler.toModel(tag));
    }

    @AllRoles
    @GetMapping("/{id}")
    public ResponseEntity<TagView> getTagById(@PathVariable("id") @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        Tag tag = tagService.getById(id);
        return ResponseEntity.ok(assembler.toModel(tag));
    }

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


    @AdminRole
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
