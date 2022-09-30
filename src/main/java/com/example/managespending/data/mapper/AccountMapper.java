package com.example.managespending.data.mapper;

import com.example.managespending.data.mapper.base.AbstractModelMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper extends AbstractModelMapper<Account, AccountDTO> {
}
