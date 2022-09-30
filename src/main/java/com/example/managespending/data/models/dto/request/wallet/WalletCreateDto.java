package com.example.managespending.data.models.dto.request.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletCreateDto implements Serializable {

    private Long userId;
    private String walletName;
    private BigDecimal balance;
    private String walletCategoryName;

}
