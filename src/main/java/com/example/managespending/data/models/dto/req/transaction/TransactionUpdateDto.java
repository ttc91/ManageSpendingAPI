package com.example.managespending.data.models.dto.req.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionUpdateDto {
    private long transactionId;

    private BigDecimal newTransactionValue;
    private String newRapName;
    private String newTransactionNote;
    private String newEventName;
    private long newAccountId;
    private String newWalletName;

}
