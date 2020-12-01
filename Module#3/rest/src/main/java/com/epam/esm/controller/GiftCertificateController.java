package com.epam.esm.controller;

import com.epam.esm.component.assembler.GiftCertificateAssembler;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
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

/**
 * The type Gift certificate controller.
 */
@RestController
@RequestMapping("api/v1/giftCertificates")
@Validated
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler assembler;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     * @param assembler              the assembler
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateAssembler assembler) {
        this.giftCertificateService = giftCertificateService;
        this.assembler = assembler;
    }

    /**
     * Gets gift certificate.
     *
     * @param id the id
     * @return the gift certificate
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        GiftCertificate giftCertificate = giftCertificateService.getById(id);
        return ResponseEntity.ok(assembler.toModel(giftCertificate));
    }


    /**
     * Gets list gift certificates by tag names.
     *
     * @param tagNames the tag names
     * @param sort     the sort
     * @param page     the page
     * @param size     the size
     * @return the list gift certificates by tag names
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/tags")
    public ResponseEntity<CollectionModel<GiftCertificate>> getListGiftCertificatesByTagNames(@RequestParam(value = "name") @NotNull List<String> tagNames,
                                                                                              @RequestParam(required = false) String sort,
                                                                                              @RequestParam(required = false) @Min(0) Integer page,
                                                                                              @RequestParam(required = false) @Min(1) Integer size) throws ResourceNotFoundException {
        Page pageable = new Page(page, size);
        List<GiftCertificate> giftCertificates = giftCertificateService.getListByTagNames(tagNames, pageable, SortMode.of(sort));
        return ResponseEntity.ok(assembler.toCollectionModel(giftCertificates));
    }

    /**
     * Gets list gift certificates.
     *
     * @param sort the sort
     * @param page the page
     * @param size the size
     * @return the list gift certificates
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping
    public ResponseEntity<CollectionModel<GiftCertificate>> getListGiftCertificates(@RequestParam(required = false) String sort,
                                                                                    @RequestParam(required = false) @Min(0) Integer page,
                                                                                    @RequestParam(required = false) @Min(1) Integer size)
            throws ResourceNotFoundException {

        Page pageable = new Page(page, size);
        List<GiftCertificate> giftCertificates = giftCertificateService.getAll(pageable, SortMode.of(sort));
        return ResponseEntity.ok(assembler.toCollectionModel(giftCertificates));
    }


    /**
     * Create gift certificate response entity.
     *
     * @param giftCertificate      the gift certificate
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
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

    /**
     * Update or create gift certificate response entity.
     *
     * @param id                   the id
     * @param giftCertificate      the gift certificate
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateOrCreateGiftCertificate(@PathVariable("id") @Min(1) Long id,
                                                                         @RequestBody @Valid GiftCertificate giftCertificate,
                                                                         UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException {

        try {
            GiftCertificate updatedGftCertificate = giftCertificateService.update(giftCertificate, id);
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

    /**
     * Update part of gift certificate response entity.
     *
     * @param id                   the id
     * @param patchGiftCertificate the patch gift certificate
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartOfGiftCertificate(@PathVariable("id") @Min(1) Long id,
                                                              @RequestBody @Valid PatchGiftCertificate patchGiftCertificate)
            throws ResourceNotFoundException, BadParametersException {

        GiftCertificate existingGiftCertificates = giftCertificateService.getById(id);
        patchGiftCertificate.mergeToEntity(existingGiftCertificates);
        GiftCertificate updatedGftCertificate = giftCertificateService.update(existingGiftCertificates, id);
        return ResponseEntity.ok(assembler.toModel(updatedGftCertificate));
    }

    /**
     * Delete gift certificate response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
