package edu.evgen.crm_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name is required")
    @Size(min = 2, message = "name must be longer than 1")
    private String name;

    @NotNull(message = "ContactInfo is required")
    @Size(min = 2, message = "contactInfo must be longer than 1")
    private String contactInfo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registrationDate;
}
