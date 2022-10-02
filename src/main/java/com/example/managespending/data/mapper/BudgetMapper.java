package com.example.managespending.data.mapper;

import com.example.managespending.data.mapper.base.AbstractModelMapper;
import com.example.managespending.data.models.dto.BudgetDTO;
import com.example.managespending.data.models.entities.Budget;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper extends AbstractModelMapper<Budget, BudgetDTO> {
}
