package com.epam.esm.controller;

import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * The type Gift certificate controller.
 */
@RestController
@RequestMapping("/giftCertificates")
@Validated
public class GiftCertificateController {
    @Autowired
    private GiftCertificateService giftCertificateService;

    /**
     * Gets gift certificate.
     *
     * @param id the id
     * @return the gift certificate
     * @throws ResourceNotFoundException the resource not found exception
     * @throws ServiceException          the service exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException, ServiceException {

        return ResponseEntity.ok(giftCertificateService.getGiftCertificatesById(id));
    }

    /**
     * Gets list gift certificates by tag name.
     *
     * @param tagName the tag name
     * @param sort    the sort
     * @return the list gift certificates by tag name
     * @throws ResourceNotFoundException the resource not found exception
     * @throws ServiceException          the service exception
     */
    @GetMapping("/tags")
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificatesByTagName(@RequestParam(value = "name") @NotEmpty String tagName,
                                                                                  @RequestParam(required = false) String sort)
            throws ResourceNotFoundException, ServiceException {
        return ResponseEntity.ok(giftCertificateService.getListGiftCertificatesWithTagsByTagName(tagName, SortMode.of(sort)));
    }

    /**
     * Gets list gift certificates.
     *
     * @param query the query
     * @param sort  the sort
     * @return the list gift certificates
     * @throws ResourceNotFoundException the resource not found exception
     * @throws ServiceException          the service exception
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificates(@RequestParam(required = false) String query,
                                                                         @RequestParam(required = false) String sort)
            throws ResourceNotFoundException, ServiceException {
        if (query == null) {
            return ResponseEntity.ok(giftCertificateService.getAllListGiftCertificatesWithTags(SortMode.of(sort)));
        } else {
            return ResponseEntity.ok(giftCertificateService.getListGiftCertificatesWithTagsBySearch(query, SortMode.of(sort)));
        }
    }

    /**
     * Create gift certificate response entity.
     *
     * @param giftCertificate      the gift certificate
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws ServiceException              the service exception
     * @throws ResourceAlreadyExistException the resource already exist exception
     */
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

    /**
     * Update or create gift certificate response entity.
     *
     * @param id                   the id
     * @param giftCertificate      the gift certificate
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws ServiceException              the service exception
     * @throws ResourceAlreadyExistException the resource already exist exception
     */
    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateOrCreateGiftCertificate(@PathVariable("id") @Min(1) Long id,
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

    /**
     * Delete gift certificate response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException          the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable("id") @Min(1) Long id)
            throws ServiceException, ResourceNotFoundException {

        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
