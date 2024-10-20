package edu.evgen.crm_system.controller;

import edu.evgen.crm_system.entity.Seller;
import edu.evgen.crm_system.repository.SellerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    private final SellerRepository sellerRepository;

    @Autowired
    public SellerController(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {

        return ResponseEntity
                .ok(StreamSupport
                        .stream(sellerRepository
                                .findAll()
                                .spliterator(), false)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getById(@PathVariable Long id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        return seller
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .notFound()
                                .build());
    }

    @PostMapping
    public ResponseEntity<?> createSeller(
            @Valid @RequestBody Seller seller,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        seller.setRegistrationDate(new Date());
        try {
            Seller createdSeller = sellerRepository.save(seller);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSeller);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create transaction: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(
            @PathVariable Long id,
            @Valid @RequestBody Seller sellerDetails,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Optional<Seller> currentSeller = sellerRepository.findById(id);

        if (currentSeller.isPresent()) {
            Seller sellerToUpdate = currentSeller.get();
            sellerToUpdate.setName(sellerDetails.getName());
            sellerToUpdate.setContactInfo(sellerDetails.getContactInfo());

            Seller updatedSeller = sellerRepository.save(sellerToUpdate);
            return ResponseEntity.ok(updatedSeller);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        if (sellerRepository.existsById(id)) {
            sellerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
