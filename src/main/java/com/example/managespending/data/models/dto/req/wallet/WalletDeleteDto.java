package com.example.managespending.data.models.dto.req.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDeleteDto {

    private String walletName;
    private Long accountId;

}
