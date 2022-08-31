package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "wallet_id")
    private Long id;

    @Column(name = "wallet_name", length = 100, unique = true, nullable = false)
    @NotNull(message = "Please input correct value of wallet name !!!")
    private String walletName;

    @Column(name = "wallet_balance", nullable = false)
    @NotNull(message = "Please input balance !!!")
    private BigDecimal balance;

    @Column(name = "wallet_create_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "wallet_update_date")
    @UpdateTimestamp
    private Date updatedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_category_id", unique = false)
    @NotNull(message = "Please choose your wallet category !!!")
    private WalletCategory walletCategory;

    @OneToMany(mappedBy = "wallet")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "wallet")
    private Set<Budget> budgets;

}
