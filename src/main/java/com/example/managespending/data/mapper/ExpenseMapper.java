package com.example.managespending.data.mapper;

import com.example.managespending.data.mapper.base.AbstractModelMapper;
import com.example.managespending.data.models.dto.ExpenseDTO;
import com.example.managespending.data.models.entities.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper extends AbstractModelMapper<Expense, ExpenseDTO> {
}
