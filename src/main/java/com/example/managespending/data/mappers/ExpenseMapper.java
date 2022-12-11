package com.example.managespending.data.mappers;

import com.example.managespending.data.mappers.base.AbstractModelMapper;
import com.example.managespending.data.models.dto.ExpenseDTO;
import com.example.managespending.data.models.entities.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper extends AbstractModelMapper<Expense, ExpenseDTO> {
}
