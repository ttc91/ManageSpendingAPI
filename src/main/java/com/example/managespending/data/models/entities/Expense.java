package com.example.managespending.data.models.entities;

import com.example.managespending.utils.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long expenseId;

    @Column(length = 30, nullable = false, unique = true)
    private String expenseName;

    @Column(nullable = false)
    private ExpenseType expenseType;

    @Column(nullable = false)
    private String expenseIcon;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "expense")
    private Set<Budget> budgets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expense")
    private Set<History> histories;

}
