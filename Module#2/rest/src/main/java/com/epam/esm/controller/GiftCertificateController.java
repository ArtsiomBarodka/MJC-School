package com.epam.esm.controller;

import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {
    @Autowired
    private GiftCertificateService giftCertificateService;

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getGiftCertificate(@PathVariable("id") @NotNull @Positive Long id)
            throws ResourceNotFoundException, ServiceException {

        return ResponseEntity.ok(giftCertificateService.getGiftCertificatesById(id));
    }

    @GetMapping("/tags/{name}")
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificatesByTagName(@PathVariable("name") @NotEmpty String tagName,
                                                                        @RequestParam("sort") @NotEmpty String sort)
            throws ResourceNotFoundException, ServiceException {

        return ResponseEntity.ok(giftCertificateService.getListGiftCertificatesWithTagsByTagName(tagName, SortMode.of(sort)));
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificatesByQuery(@RequestParam("query") @NotEmpty String query,
                                                                                  @RequestParam("sort") @NotEmpty String sort)
            throws ResourceNotFoundException, ServiceException {

        return ResponseEntity.ok(giftCertificateService.getListGiftCertificatesWithTagsBySearch(query, SortMode.of(sort)));
    }

    @PostMapping
    public ResponseEntity<Object> createGiftCertificate(@RequestBody @Valid GiftCertificate giftCertificate,
                                                        UriComponentsBuilder uriComponentsBuilder)
            throws ServiceException, ResourceAlreadyExistException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/giftCertificates/{id}")
                        .buildAndExpand(giftCertificateService.create(giftCertificate))
                        .toUri())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateOrCreateGiftCertificate(@PathVariable("id") @NotNull @Positive Long id,
                                                                         @RequestBody @Valid GiftCertificate giftCertificate,
                                                                         UriComponentsBuilder uriComponentsBuilder)
            throws ServiceException, ResourceAlreadyExistException {

        try {
            return ResponseEntity.ok(giftCertificateService.update(giftCertificate, id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.created(
                    uriComponentsBuilder
                            .path("/giftCertificates/{id}")
                            .buildAndExpand(giftCertificateService.create(giftCertificate))
                            .toUri())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable("id") @NotNull @Positive Long id)
            throws ServiceException, ResourceNotFoundException {

        giftCertificateService.getGiftCertificatesById(id);
        return ResponseEntity.noContent().build();
    }

}
