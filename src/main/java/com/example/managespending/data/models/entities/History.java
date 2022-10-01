package com.example.managespending.data.models.entities;

import com.example.managespending.utils.enums.HistoryAction;
import com.example.managespending.utils.enums.HistoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "history")
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long historyId;

    @Column(nullable = false)
    private HistoryType historyType;

    @Column
    @CreationTimestamp
    private Date historyNotedDate;

    @Column
    private HistoryAction historyAction;

    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0 NOT NULL")
    private BigDecimal historyCost;

    @Column
    private String historyNote;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

}
