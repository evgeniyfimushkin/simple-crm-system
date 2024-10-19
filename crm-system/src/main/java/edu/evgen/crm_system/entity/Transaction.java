package edu.evgen.crm_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private Long amount;

    private PaymentType paymentType;

    private Date transactionalDate;
}
