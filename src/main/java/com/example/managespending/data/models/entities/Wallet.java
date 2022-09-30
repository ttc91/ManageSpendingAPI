package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(length = 100, nullable = false)
    private String walletName;

    @Column(nullable = false)
    private BigDecimal walletBalance;

    @Column
    @CreationTimestamp
    private Date createdDate;

    @Column
    @UpdateTimestamp
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


//    @ManyToOne
//    @JoinColumn(name = "wallet_category_id", nullable = false)
//    @NotNull(message = "Please choose your wallet category !!!")
//    private WalletCategory walletCategory;
//
//    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
//    private Set<Transaction> transactions;
//
//    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
//    private Set<Budget> budgets;

}
