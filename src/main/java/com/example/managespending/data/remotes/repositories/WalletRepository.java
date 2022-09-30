package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByAccountAndWalletName(Account account, String walletName);
    List<Wallet> findAllByAccount(Account account);

}
