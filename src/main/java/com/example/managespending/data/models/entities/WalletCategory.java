package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "wallet_category")
public class WalletCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "wallet_category_id")
    private Long id;

    @Column(name = "wallet_category_name", nullable = false, length = 50, unique = true)
    @NotNull(message = "Please input correct value of wallet category name !!!")
    private String walletCategoryName;

    @OneToMany(mappedBy = "walletCategory")
    private Set<Wallet> wallets;
}
