package com.example.managespending.data.mappers;

import com.example.managespending.data.mappers.base.AbstractModelMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper extends AbstractModelMapper<Account, AccountDTO> {
}
