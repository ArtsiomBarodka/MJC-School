package com.epam.esm.rest.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.model.request.GiftCertificateRequest;
import com.epam.esm.model.request.PatchGiftCertificate;
import com.epam.esm.model.view.GiftCertificateView;
import com.epam.esm.rest.hateoas.GiftCertificateAssembler;
import com.epam.esm.security.annotation.AdminRole;
import com.epam.esm.service.GiftCertificateService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * The type Gift certificate controller.
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/giftCertificates")
@Validated
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler assembler;

    /**
     * Gets gift certificate.
     *
     * @param id the id
     * @return the gift certificate
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateView> getGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        GiftCertificate giftCertificate = giftCertificateService.getById(id);
        return ResponseEntity.ok(assembler.toModel(giftCertificate));
    }

    /**
     * Gets list gift certificates by tag names.
     *
     * @param tagNames                the tag names
     * @param pageable                the pageable
     * @param pagedResourcesAssembler the paged resources assembler
     * @return the list gift certificates by tag names
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @GetMapping("/tags")
    public ResponseEntity<PagedModel<GiftCertificateView>> getListGiftCertificatesByTagNames(@RequestParam(value = "name") List<String> tagNames,
                                                                                             @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
                                                                                             PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler)
            throws ResourceNotFoundException, BadParametersException {

        Page<GiftCertificate> giftCertificates = giftCertificateService.getListByTagNames(tagNames, pageable);
        PagedModel<GiftCertificateView> pagedModel = pagedResourcesAssembler.toModel(giftCertificates, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Gets list gift certificates.
     *
     * @param pageable                the pageable
     * @param pagedResourcesAssembler the paged resources assembler
     * @return the list gift certificates
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping
    public ResponseEntity<PagedModel<GiftCertificateView>> getListGiftCertificates(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
                                                                                   PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<GiftCertificate> giftCertificates = giftCertificateService.getAll(pageable);
        PagedModel<GiftCertificateView> pagedModel = pagedResourcesAssembler.toModel(giftCertificates, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Create gift certificate response entity.
     *
     * @param giftCertificateRequest the gift certificate request
     * @param uriComponentsBuilder   the uri components builder
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @AdminRole
    @PostMapping
    public ResponseEntity<Object> createGiftCertificate(@RequestBody @Valid GiftCertificateRequest giftCertificateRequest,
                                                        UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/giftCertificates/{id}")
                        .buildAndExpand(giftCertificateService.create(GiftCertificateRequest.toGiftCertificate(giftCertificateRequest)))
                        .toUri())
                .build();
    }

    /**
     * Update or create gift certificate response entity.
     *
     * @param id                     the id
     * @param giftCertificateRequest the gift certificate request
     * @param uriComponentsBuilder   the uri components builder
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @AdminRole
    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateView> updateOrCreateGiftCertificate(@PathVariable("id") @Min(1) Long id,
                                                                             @RequestBody @Valid GiftCertificateRequest giftCertificateRequest,
                                                                             UriComponentsBuilder uriComponentsBuilder)
            throws ResourceAlreadyExistException, BadParametersException {

        GiftCertificate giftCertificate = GiftCertificateRequest.toGiftCertificate(giftCertificateRequest);
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
    @AdminRole
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
    @AdminRole
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
