package com.example.managespending.data.models.entities.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Embeddable
public class WalletKey implements Serializable {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "wallet_name", length = 100, nullable = false)
    @NotNull(message = "Please input correct value of wallet name !!!")
    private String walletName;

    public WalletKey() {
    }

    public WalletKey(Long accountId, String walletName) {
        this.accountId = accountId;
        this.walletName = walletName;
    }

}
