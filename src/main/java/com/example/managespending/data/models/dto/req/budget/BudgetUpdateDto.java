package com.example.managespending.data.models.dto.req.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetUpdateDto {
    private String oldRapName;
    private BigDecimal newBudgetValue;
    private String newRapName;
    private  long accountId;
    private String walletName;
}
