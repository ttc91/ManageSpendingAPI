package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.req.account.AccountRegistrationDto;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.remotes.service.AccountService;
import com.example.managespending.utils.PathApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(PathApi.ACCOUNT_INSERT_DOMAIN)
    public ResponseEntity<Account> insert(@Valid @RequestBody AccountRegistrationDto request){

        if(request.getPassword().equals(request.getRePassword())){

            Account account = modelMapper.map(request, Account.class);
            service.save(account);
            return new ResponseEntity<Account>(HttpStatus.CREATED);

        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
