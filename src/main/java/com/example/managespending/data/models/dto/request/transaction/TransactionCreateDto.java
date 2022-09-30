package com.example.managespending.data.models.dto.request.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionCreateDto {

    private BigDecimal transactionValue;
    private String rapName;
    private String transactionNote;
    private String eventName;
    private long accountId;
    private String walletName;

}
