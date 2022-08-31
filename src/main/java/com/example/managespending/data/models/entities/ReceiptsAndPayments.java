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
public class ReceiptsAndPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "receipts_and_payments_id")
    private Long id;

    @Column(name = "receipts_and_payments_name", nullable = false, length = 50, unique = true)
    @NotNull(message = "Please input correct value of budget name !!!")
    private String budgetName;

    @Column(name = "receipts_and_payments_logo", nullable = false, length = 1000)
    @NotNull(message = "Please input budget logo !!!")
    private String budgetLogo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipts_and_payments_category_id", nullable = false)
    @NotNull(message = "Please input choose your category of receipts and payments !!!")
    private ReceiptsAndPaymentsCategory receiptsAndPaymentsCategory;

    @OneToMany(mappedBy = "receiptsAndPayments")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "receiptsAndPayments")
    private Set<Budget> budgets;

}
