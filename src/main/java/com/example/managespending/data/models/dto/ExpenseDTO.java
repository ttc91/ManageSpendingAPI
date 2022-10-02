package com.example.managespending.data.models.dto;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.utils.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDTO extends BaseDTO implements Serializable {

    private Long expenseId;

    private String expenseName;

    private ExpenseType expenseType;

    private String expenseIcon;

    private AccountDTO account;

    private List<BudgetDTO> budgets;

    private List<HistoryDTO> histories;

}
