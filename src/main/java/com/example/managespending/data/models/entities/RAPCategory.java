package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "receipts_and_payments_category")
public class RAPCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipts_and_payments_category_id")
    private Long id;

    @Column(name = "receipts_and_payments_category_name", nullable = false, length = 50, unique = true)
    @NotNull(message = "Please input correct value of budget category name !!!")
    private String rapCategoryName;

    @OneToMany(mappedBy = "receiptsAndPaymentsCategory", cascade = CascadeType.ALL)
    private Set<RAP> receiptsAndPayments;

}
