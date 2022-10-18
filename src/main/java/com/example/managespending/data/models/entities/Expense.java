package com.example.managespending.data.models.entities;

import com.example.managespending.utils.enums.ExpenseType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long expenseId;

    @Column(length = 30, nullable = false)
    private String expenseName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @Column(nullable = false)
    private String expenseIcon;

    @Column(nullable = false)
    private Boolean isExpenseSystem;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "expense")
    private List<Budget> budgets;

    @OneToMany(mappedBy = "expense")
    private List<History> histories;

    @PreRemove
    public void setHistoryNull (){
        this.histories.forEach(h -> h.setExpense(null));
    }

}
