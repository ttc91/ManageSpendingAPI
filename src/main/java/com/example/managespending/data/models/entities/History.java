package com.example.managespending.data.models.entities;

import com.example.managespending.utils.enums.HistoryAction;
import com.example.managespending.utils.enums.HistoryType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

@Entity
@Table(name = "history")
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long historyId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HistoryType historyType;

    @Column
    @CreationTimestamp
    private Date historyNotedDate;

    @Column
    @Enumerated(EnumType.STRING)
    private HistoryAction historyAction;

    @Column
    private BigDecimal historyCost;

    @Column
    private String historyNote;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonBackReference
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @JsonBackReference
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    @JsonBackReference
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    @JsonBackReference
    private Budget budget;

}
