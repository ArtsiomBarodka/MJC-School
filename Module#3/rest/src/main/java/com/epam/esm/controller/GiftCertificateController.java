package com.epam.esm.controller;

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
    @Autowired
    private GiftCertificateService giftCertificateService;

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getGiftCertificate(@PathVariable("id") @Min(1) Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(giftCertificateService.getGiftCertificatesById(id));
    }


    @GetMapping("/tags")
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificatesByTagNames(@RequestParam(value = "name") @NotNull List<String> tagNames,
                                                                                   @RequestParam(required = false) String sort,
                                                                                   @RequestParam(required = false) @Min(0) Integer page,
                                                                                   @RequestParam(required = false) @Min(1) Integer size) throws ResourceNotFoundException {
        Page pageable = new Page(page, size);
        return ResponseEntity.ok(giftCertificateService.getListGiftCertificatesWithTagsByTagNames(tagNames, pageable, SortMode.of(sort)));
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificates(@RequestParam(required = false) String sort,
                                                                         @RequestParam(required = false) @Min(0) Integer page,
                                                                         @RequestParam(required = false) @Min(1) Integer size)
            throws ResourceNotFoundException {
        Page pageable = new Page(page, size);
        return ResponseEntity.ok(giftCertificateService.getAllListGiftCertificatesWithTags(pageable, SortMode.of(sort)));
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
            return ResponseEntity.ok(giftCertificateService.updateAndReturn(giftCertificate, id));
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
        return ResponseEntity.ok(giftCertificateService.updateAndReturn(existingGiftCertificates, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
