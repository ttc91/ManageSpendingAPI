package com.example.managespending.data.models.dto;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BudgetDTO extends BaseDTO implements Serializable {

    private Long budgetId;

    private String budgetName;

    private String budgetIcon;

    private BigDecimal budgetValue;

    private String budgetMothYear;

    private Boolean budgetStatus;

    @JsonBackReference
    private AccountDTO account;

    private ExpenseDTO expense;

    private List<HistoryDTO> histories;

}
