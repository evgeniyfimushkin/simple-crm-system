package edu.evgen.crm_system.controller;

import edu.evgen.crm_system.entity.Transaction;
import edu.evgen.crm_system.repository.SellerRepository;
import edu.evgen.crm_system.repository.TransactionRepository;
import edu.evgen.crm_system.service.TransactionService;
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
@RequestMapping("/transactions")
public class TransactionController {
    TransactionRepository transactionRepository;
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @GetMapping
    ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(StreamSupport
                .stream(transactionRepository
                        .findAll().
                        spliterator(), false)
                .toList());
    }

    @GetMapping("/{id}")
    ResponseEntity<Transaction> getTransactionById(
            @PathVariable Long id) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(id);

        return existingTransaction
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .notFound()
                                .build());
    }

    @PostMapping
    ResponseEntity<?> createTransaction(
            @Valid @RequestBody Transaction transaction,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        transaction.setTransactionalDate(new Date());
        try {
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create transaction: " + e.getMessage());
        }
    }

//    @PutMapping("/{id}")
//    ResponseEntity<?> updateTransaction(
//            @PathVariable Long id,
//            @RequestBody Transaction transaction) {
//        Optional<Transaction> existingTransaction = transactionRepository.findById(id);
//        if (existingTransaction.isPresent()) {
//            Transaction transactionToUpdate = existingTransaction.get();
//            try {
////                Transaction updatedTransaction = transactionService.updateTransaction(transactionToUpdate, id);
////                return ResponseEntity.ok(updatedTransaction);
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create transaction:
//                " + e.getMessage());
//            }
//
//        }else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
