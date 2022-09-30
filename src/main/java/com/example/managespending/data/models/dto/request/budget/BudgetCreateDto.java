package com.example.managespending.data.models.dto.request.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetCreateDto {

    private String rapName;
    private BigDecimal budgetValue;
    private long accountId;
    private String walletName;
    private Date createDate;

}
