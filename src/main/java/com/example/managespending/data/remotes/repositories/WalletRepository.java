package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Wallet;
import com.example.managespending.data.models.entities.key.WalletKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, WalletKey> {

    public void deleteById(WalletKey key);

}
