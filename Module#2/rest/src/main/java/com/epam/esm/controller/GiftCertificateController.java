package com.epam.esm.controller;

import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
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
import java.util.Optional;

@RestController
public class GiftCertificateController {
    @Autowired
    private GiftCertificateService giftCertificateService;

    @GetMapping("/giftCertificates/{id}")
    public ResponseEntity<GiftCertificate> getGiftCertificate(@PathVariable("id") @NotNull @Positive Long id){
        Optional<GiftCertificate> giftCertificates = giftCertificateService.getGiftCertificatesById(id);
        if(giftCertificates.isPresent()){
            return ResponseEntity.ok(giftCertificates.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/giftCertificates")
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificatesByTagName(@RequestParam("tag") @NotEmpty String tagName,
                                                                        @RequestParam("sort") @NotEmpty String sort){
        List<GiftCertificate> listGiftCertificatesWithTagsByTagName = giftCertificateService.getListGiftCertificatesWithTagsByTagName(tagName, SortMode.of(sort));
        return  listGiftCertificatesWithTagsByTagName.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(listGiftCertificatesWithTagsByTagName);

    }

    @GetMapping("/giftCertificates")
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificatesByQuery(@RequestParam("query") @NotEmpty String query,
                                                                                  @RequestParam("sort") @NotEmpty String sort){
        List<GiftCertificate> listGiftCertificatesWithTagsByTagName = giftCertificateService.getListGiftCertificatesWithTagsBySearch(query, SortMode.of(sort));
        return  listGiftCertificatesWithTagsByTagName.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(listGiftCertificatesWithTagsByTagName);

    }

    @PostMapping("/giftCertificates")
    public ResponseEntity<Object> createGiftCertificate(@RequestBody @Valid GiftCertificate giftCertificate,
                                                        UriComponentsBuilder uriComponentsBuilder){
        if(giftCertificateService.isAlreadyExist(giftCertificate.getName())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Long newId = giftCertificateService.create(giftCertificate);
        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/giftCertificates/{id}")
                        .buildAndExpand(newId)
                        .toUri())
                .build();
    }

    @PutMapping("/giftCertificates/{id}")
    public ResponseEntity<GiftCertificate> updateOrCreateGiftCertificate(@PathVariable("id") @NotNull @Positive Long id,
                                                                         @RequestBody @Valid GiftCertificate giftCertificate,
                                                                         UriComponentsBuilder uriComponentsBuilder){
        if(giftCertificateService.getGiftCertificatesById(id).isPresent()){
            return ResponseEntity.ok(giftCertificateService.update(giftCertificate, id));
        }
        Long newId = giftCertificateService.create(giftCertificate);
        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/giftCertificates/{id}")
                        .buildAndExpand(newId)
                        .toUri())
                .build();
    }

    @DeleteMapping("/giftCertificates/{id}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable("id") @NotNull @Positive Long id){
        if(giftCertificateService.getGiftCertificatesById(id).isPresent()){
            giftCertificateService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
