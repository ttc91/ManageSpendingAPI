package com.example.managespending.data.models.entities;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;
//
//    @Column(name = "transaction_value", nullable = false)
//    @NotNull(message = "Please input transaction value !!!")
//    private BigDecimal transactionValue;
//
//    @Column(name = "transaction_create_date")
//    @CreationTimestamp
//    private Date createdDate;
//
//    @Column(name = "transaction_note", length = 500, nullable = true)
//    private String transactionNote;
//
//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false),
//            @JoinColumn(name = "wallet_name", referencedColumnName = "wallet_name", nullable = false)
//    })
//    @NotNull(message = "Please choose your wallet !!!")
//    private Wallet wallet;
//
//    @ManyToOne
//    @JoinColumn(name = "receipts_and_payments_id", nullable = false)
//    @NotNull(message = "Please choose your receipts and payments in this transaction !!!")
//    private RAP receiptsAndPayments;
//
//    @ManyToOne
//    @JoinColumn(name = "transaction", nullable = true)
//    private Event event;

}
