package edu.evgen.crm_system.service;

import edu.evgen.crm_system.entity.Seller;
import edu.evgen.crm_system.entity.Transaction;
import edu.evgen.crm_system.repository.SellerRepository;
import edu.evgen.crm_system.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    public TransactionService(TransactionRepository transactionRepository, SellerRepository sellerRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        Seller seller = sellerRepository.findById(transaction.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
        transaction.setSeller(seller);
        transaction.setTransactionalDate(new Date());
        return transactionRepository.save(transaction);
    }

    // Пришла транзакция, и она точно есть в репозитории
//    @Transactional
//    public Transaction updateTransaction(Transaction transaction, Long id){
//        Optional<Seller> seller = sellerRepository.findById(transaction.getSellerId());
//
//    }


}
