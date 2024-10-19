package edu.evgen.crm_system.repository;

import edu.evgen.crm_system.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
