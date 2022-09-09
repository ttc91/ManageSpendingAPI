package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.req.account.AccountDeleteDto;
import com.example.managespending.data.models.dto.req.account.AccountRegistrationDto;
import com.example.managespending.data.models.dto.req.account.AccountUpdateDto;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.remotes.service.AccountService;
import com.example.managespending.utils.PathApi;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(PathApi.ACCOUNT_DOMAIN)
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(PathApi.MODEL_INSERT_DOMAIN)
    public ResponseEntity<Account> insert(@RequestBody AccountRegistrationDto request) {

        if (request.getPassword().equals(request.getRePassword())) {
            Account account = modelMapper.map(request, Account.class);
            account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12)));
            service.save(account);
            return new ResponseEntity<>(account, HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(PathApi.MODEL_UPDATE_DOMAIN)
    public ResponseEntity<Account> update(@RequestBody AccountUpdateDto request) {

        Account account = service.findAccountByUsername(request.getUsername());
        if (account != null && BCrypt.checkpw(request.getOldPassword(), account.getPassword()) &&
                request.getNewPassword().equals(request.getReNewPassword()) && (request.getOldPassword() != request.getNewPassword())) {

            account.setPassword(BCrypt.hashpw(request.getNewPassword(), BCrypt.gensalt(12)));
            service.save(account);
            return new ResponseEntity<>(account, HttpStatus.UPGRADE_REQUIRED);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(PathApi.MODEL_DELETE_DOMAIN)
    public ResponseEntity<Account> delete(@RequestBody AccountDeleteDto request) {

        Account account = service.findAccountByUsername(request.getUsername());
        if (request != null) {
            service.delete(account);
            return new ResponseEntity<>(account, HttpStatus.UPGRADE_REQUIRED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
