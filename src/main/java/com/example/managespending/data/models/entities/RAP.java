package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "receipts_and_payments")
public class RAP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipts_and_payments_id")
    private Long id;

    @Column(name = "receipts_and_payments_name", nullable = false, length = 50, unique = true)
    @NotNull(message = "Please input correct value of budget name !!!")
    private String rapName;

    @Column(name = "receipts_and_payments_logo", nullable = false, length = 1000)
    @NotNull(message = "Please input budget logo !!!")
    private String rapLogo;

    @ManyToOne
    @JoinColumn(name = "receipts_and_payments_category_id", nullable = false)
    @NotNull(message = "Please input choose your category of receipts and payments !!!")
    private RAPCategory rapCategory;

    @OneToMany(mappedBy = "receiptsAndPayments", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    @OneToOne(mappedBy = "receiptsAndPayments", cascade = CascadeType.ALL)
    private Budget budgets;

}
