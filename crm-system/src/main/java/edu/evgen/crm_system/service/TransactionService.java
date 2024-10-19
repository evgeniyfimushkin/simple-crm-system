package edu.evgen.crm_system.service;

import edu.evgen.crm_system.entity.Seller;
import edu.evgen.crm_system.entity.Transaction;
import edu.evgen.crm_system.repository.SellerRepository;
import edu.evgen.crm_system.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    public TransactionService(TransactionRepository transactionRepository, SellerRepository sellerRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        Seller seller = sellerRepository.findById(transaction.getSeller().getId())
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
        transaction.setSeller(seller);
        transaction.setTransactionalDate(new Date());
        return transactionRepository.save(transaction);
    }


    // Пришла транзакция, и она точно есть в репозитории
    public Transaction updateTransaction(Transaction transaction, Long id) {

        Optional<Seller> existingSeller = sellerRepository.findById(transaction.getSeller().getId());
        if (existingSeller.isPresent()) {
            transaction.setSeller(existingSeller.get());

            Optional<Transaction> transactionToUpdateOpt = transactionRepository.findById(id);
            if (transactionToUpdateOpt.isEmpty()) {
                throw new IllegalArgumentException("Transaction with id " + id + " not found");
            }


            Transaction transactionToUpdate = transactionToUpdateOpt.get();


            transactionToUpdate.setAmount(transaction.getAmount());
            transactionToUpdate.setSeller(transaction.getSeller());
            transactionToUpdate.setPaymentType(transaction.getPaymentType());

            return transactionRepository.save(transactionToUpdate);

        } else {
            throw new IllegalArgumentException("Seller with id " + transaction.getSeller().getId() + " not found");
        }
    }


}
