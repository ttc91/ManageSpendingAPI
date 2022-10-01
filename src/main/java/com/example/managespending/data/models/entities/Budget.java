package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "budget")
public class Budget implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long budgetId;

    @Column(nullable = false)
    private String budgetName;

    @Column(nullable = false)
    private BigDecimal budgetValue;

    @Column(nullable = false)
    private String budgetMothYear;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

}
