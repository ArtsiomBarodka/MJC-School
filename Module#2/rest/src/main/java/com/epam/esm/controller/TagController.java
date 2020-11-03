package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
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

@Controller
@RequestMapping("/tags")
@Validated
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") @NotNull @Min(1) Long id)
            throws ResourceNotFoundException, ServiceException {

        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @PostMapping
    public ResponseEntity<Object> createTag(@RequestBody @Valid Tag tag,
                                            UriComponentsBuilder uriComponentsBuilder)
            throws ServiceException, ResourceAlreadyExistException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/tags/{id}")
                        .buildAndExpand(tagService.create(tag))
                        .toUri())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException, ServiceException {

        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
