package com.example.managespending.data.models.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "transaction_value", nullable = false)
    @NotNull(message = "Please input transaction value !!!")
    private BigDecimal transactionValue;

    @Column(name = "transaction_create_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "transaction_note", length = 500, nullable = true)
    private String transactionNote;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", nullable = false)
    @NotNull(message = "Please choose your wallet !!!")
    private Wallet wallet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipts_and_payments_id", nullable = false)
    @NotNull(message = "Please choose your receipts and payments in this transaction !!!")
    private ReceiptsAndPayments receiptsAndPayments;

    @OneToOne(mappedBy = "transaction")
    private Event event;

}
