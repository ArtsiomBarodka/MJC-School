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
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/giftCertificates")
@Validated
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler assembler;

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateView> getGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        GiftCertificate giftCertificate = giftCertificateService.getById(id);
        return ResponseEntity.ok(assembler.toModel(giftCertificate));
    }

    @GetMapping("/tags")
    public ResponseEntity<PagedModel<GiftCertificateView>> getListGiftCertificatesByTagNames(@RequestParam(value = "name") @NotNull List<String> tagNames,
                                                                                             @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
                                                                                             PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<GiftCertificate> giftCertificates = giftCertificateService.getListByTagNames(tagNames, pageable);
        PagedModel<GiftCertificateView> pagedModel = pagedResourcesAssembler.toModel(giftCertificates, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping
    public ResponseEntity<PagedModel<GiftCertificateView>> getListGiftCertificates(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
                                                                                   PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<GiftCertificate> giftCertificates = giftCertificateService.getAll(pageable);
        PagedModel<GiftCertificateView> pagedModel = pagedResourcesAssembler.toModel(giftCertificates, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

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

    @AdminRole
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
