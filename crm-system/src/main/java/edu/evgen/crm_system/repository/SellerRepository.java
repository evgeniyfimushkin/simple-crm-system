package edu.evgen.crm_system.repository;

import edu.evgen.crm_system.entity.Seller;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller, Long> {
    Seller findSellerByName(String name);
}
