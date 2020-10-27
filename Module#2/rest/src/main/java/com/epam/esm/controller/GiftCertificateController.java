package com.epam.esm.controller;

import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(giftCertificates.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/giftCertificates")
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificatesByTagName(@RequestParam("tag") @NotEmpty String tagName,
                                                                        @RequestParam("sort") @NotEmpty String sort){
        List<GiftCertificate> listGiftCertificatesWithTagsByTagName = giftCertificateService.getListGiftCertificatesWithTagsByTagName(tagName, SortMode.of(sort));
        return  listGiftCertificatesWithTagsByTagName.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(listGiftCertificatesWithTagsByTagName, HttpStatus.OK);

    }

    @GetMapping("/giftCertificates")
    public ResponseEntity<List<GiftCertificate>> getListGiftCertificatesByQuery(@RequestParam("query") @NotEmpty String query,
                                                                                  @RequestParam("sort") @NotEmpty String sort){
        List<GiftCertificate> listGiftCertificatesWithTagsByTagName = giftCertificateService.getListGiftCertificatesWithTagsBySearch(query, SortMode.of(sort));
        return  listGiftCertificatesWithTagsByTagName.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(listGiftCertificatesWithTagsByTagName, HttpStatus.OK);

    }

    @PostMapping("/giftCertificates")
    public ResponseEntity<Object> createGiftCertificate(@RequestBody @Valid GiftCertificate giftCertificate){
        if(giftCertificateService.isAlreadyExist(giftCertificate.getName())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        giftCertificateService.create(giftCertificate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/giftCertificates/{id}")
    public ResponseEntity<GiftCertificate> updateOrCreateGiftCertificate(@PathVariable("id") @NotNull @Positive Long id,
                                                   @RequestBody @Valid GiftCertificate giftCertificate){
        if(giftCertificateService.getGiftCertificatesById(id).isPresent()){
            return new ResponseEntity<> (giftCertificateService.update(giftCertificate, id),HttpStatus.OK);
        }
        Long newId = giftCertificateService.create(giftCertificate);
        return new ResponseEntity<> (giftCertificateService.getGiftCertificatesById(newId).get(),HttpStatus.CREATED);
    }

    @DeleteMapping("/giftCertificates/{id}")
    public ResponseEntity deleteGiftCertificate(@PathVariable("id") @NotNull @Positive Long id){
        if(giftCertificateService.getGiftCertificatesById(id).isPresent()){
            giftCertificateService.delete(id);
            return new ResponseEntity<> (HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<> (HttpStatus.NOT_FOUND);
    }

}
