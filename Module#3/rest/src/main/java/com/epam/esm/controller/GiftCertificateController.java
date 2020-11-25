package com.epam.esm.controller;

import com.epam.esm.component.assembler.GiftCertificateAssembler;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.patch.PatchGiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/v1/giftCertificates")
@Validated
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler assembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateAssembler assembler) {
        this.giftCertificateService = giftCertificateService;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        GiftCertificate giftCertificate = giftCertificateService.getGiftCertificatesById(id);
        return ResponseEntity.ok(assembler.toModel(giftCertificate));
    }


    @GetMapping("/tags")
    public ResponseEntity<CollectionModel<GiftCertificate>> getListGiftCertificatesByTagNames(@RequestParam(value = "name") @NotNull List<String> tagNames,
                                                                                              @RequestParam(required = false) String sort,
                                                                                              @RequestParam(required = false) @Min(0) Integer page,
                                                                                              @RequestParam(required = false) @Min(1) Integer size) throws ResourceNotFoundException {
        Page pageable = new Page(page, size);
        List<GiftCertificate> giftCertificates = giftCertificateService.getListGiftCertificatesWithTagsByTagNames(tagNames, pageable, SortMode.of(sort));
        return ResponseEntity.ok(assembler.toCollectionModel(giftCertificates));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<GiftCertificate>> getListGiftCertificates(@RequestParam(required = false) String sort,
                                                                                    @RequestParam(required = false) @Min(0) Integer page,
                                                                                    @RequestParam(required = false) @Min(1) Integer size)
            throws ResourceNotFoundException {

        Page pageable = new Page(page, size);
        List<GiftCertificate> giftCertificates = giftCertificateService.getAllListGiftCertificatesWithTags(pageable, SortMode.of(sort));
        return ResponseEntity.ok(assembler.toCollectionModel(giftCertificates));
    }


    @PostMapping
    public ResponseEntity<Object> createGiftCertificate(@RequestBody @Valid GiftCertificate giftCertificate,
                                                        UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/giftCertificates/{id}")
                        .buildAndExpand(giftCertificateService.create(giftCertificate))
                        .toUri())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateOrCreateGiftCertificate(@PathVariable("id") @Min(1) Long id,
                                                                         @RequestBody @Valid GiftCertificate giftCertificate,
                                                                         UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException, ServiceException {

        try {
            GiftCertificate updatedGftCertificate = giftCertificateService.updateAndReturn(giftCertificate, id);
            return ResponseEntity.ok(assembler.toModel(updatedGftCertificate));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.created(
                    uriComponentsBuilder
                            .path("/api/v1/giftCertificates/{id}")
                            .buildAndExpand(giftCertificateService.create(giftCertificate))
                            .toUri())
                    .build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartOfGiftCertificate(@PathVariable("id") @Min(1) Long id,
                                                              @RequestBody @Valid PatchGiftCertificate patchGiftCertificate)
            throws ResourceNotFoundException, ServiceException, BadParametersException {

        GiftCertificate existingGiftCertificates = giftCertificateService.getGiftCertificatesById(id);
        patchGiftCertificate.mergeToEntity(existingGiftCertificates);
        GiftCertificate updatedGftCertificate = giftCertificateService.updateAndReturn(existingGiftCertificates, id);
        return ResponseEntity.ok(assembler.toModel(updatedGftCertificate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
