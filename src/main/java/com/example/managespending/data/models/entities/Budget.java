package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "budget")
public class Budget implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;

    @Column(name = "budget_value", nullable = false)
    @NotNull(message = "Please input budget value !!!")
    private BigDecimal budgetValue;

    @Column(name = "budget_create_date", nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false),
            @JoinColumn(name = "wallet_name", referencedColumnName = "wallet_name", nullable = false)
    })
    @NotNull(message = "Please choose your wallet !!!")
    private Wallet wallet;

    @OneToOne
    @JoinColumn(name = "receipts_and_payments_id", nullable = false)
    @NotNull(message = "Please choose your receipts and payments in this transaction !!!")
    private RAP rap;

}
