package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.remotes.service.AccountService;
import com.example.managespending.utils.ApiPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = ApiPaths.ACCOUNT_DOMAIN)
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    AccountService service;

    @PostMapping(value = ApiPaths.MODEL_CREATE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> create (@RequestBody @Valid AccountDTO request) {

        try{
            return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = ApiPaths.ACCOUNT_SIGN_IN_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> signIn (@RequestBody @Valid AccountDTO request) {

        try{
            return new ResponseEntity<>(service.signIn(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = ApiPaths.ACCOUNT_CHANGE_PASSWORD)
    public ResponseEntity<ResponseDTO<BaseDTO>> changePassword (@RequestBody @Valid AccountDTO request) {

        try{
            return new ResponseEntity<>(service.changePassword(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
