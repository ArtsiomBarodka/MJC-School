package com.epam.esm.controller;

import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.patch.PatchTag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("api/v1/tags")
@Validated
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getListTags(@RequestParam(required = false) String sort,
                                                 @RequestParam(required = false) @Min(0) Integer page,
                                                 @RequestParam(required = false) @Min(1) Integer size)
            throws ResourceNotFoundException {
        Page pageable = new Page(page, size);
        return ResponseEntity.ok(tagService.getListAllTagsWithGiftCertificates(pageable, SortMode.of(sort)));
    }

    @GetMapping("/giftCertificates")
    public ResponseEntity<List<Tag>> getListTagsByGiftCertificatesById(@RequestParam @NotNull @Min(1) Long id,
                                                                       @RequestParam(required = false) String sort,
                                                                       @RequestParam(required = false) @Min(0) Integer page,
                                                                       @RequestParam(required = false) @Min(1) Integer size)
            throws ResourceNotFoundException {
        Page pageable = new Page(page, size);
        return ResponseEntity.ok(tagService.getListTagsWithGiftCertificatesByGiftCertificateId(id, pageable, SortMode.of(sort)));
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
            throws ResourceAlreadyExistException, BadParametersException, ServiceException {

        try {
            return ResponseEntity.ok(tagService.updateAndReturn(tag, id));
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
            throws ResourceNotFoundException, ServiceException, BadParametersException {

        Tag existingTag = tagService.getTagById(id);
        patchTag.mergeToEntity(existingTag);
        return ResponseEntity.ok(tagService.updateAndReturn(existingTag, id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
