package com.example.managespending.data.models.dto.request.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletUpdateDto {

    private Long accountId;
    private String oldWalletName;
    private String newWalletName;
    private BigDecimal balance;
    private String walletCategoryName;

}
