package edu.evgen.crm_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Transient
    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0",
            inclusive = false,
            message = "Amount must be greater than zero")
    private Long amount;

    @NotNull(message = "Payment type is required")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date transactionalDate;


}
