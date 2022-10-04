package com.example.managespending.data.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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

    @Column(length = 100, nullable = false)
    private String budgetName;

    @Column(nullable = false)
    private String budgetIcon;

    @Column
    private BigDecimal budgetPresentValue;

    @Column(nullable = false)
    private BigDecimal budgetValue;

    @Column(nullable = false)
    private String budgetMothYear;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE NOT NULL")
    private Boolean budgetStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budget")
    private List<History> histories;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    @JsonBackReference
    private Expense expense;

}
