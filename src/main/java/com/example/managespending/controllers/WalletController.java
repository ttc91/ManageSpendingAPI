package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.WalletDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.entities.Wallet;
import com.example.managespending.data.remotes.service.WalletService;
import com.example.managespending.utils.ApiPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.WALLET_DOMAIN)
@CrossOrigin(origins = "*")
public class WalletController {

    @Autowired
    WalletService service;


    @PostMapping(value = ApiPaths.MODEL_CREATE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> insert (@RequestBody @Valid WalletDTO request){

        try{
            return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = ApiPaths.MODEL_UPDATE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> update (@RequestBody WalletDTO request){

        try{
            return new ResponseEntity<>(service.update(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = ApiPaths.MODEL_DELETE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> delete(@RequestBody WalletDTO request){

        try{
            return new ResponseEntity<>(service.delete(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value =  ApiPaths.MODEL_GET_LIST_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> getAll(@RequestBody AccountDTO request){

        try{
            return new ResponseEntity<>(service.getAll(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value =  ApiPaths.MODEL_GET_ONE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> getOne(@RequestBody WalletDTO request){

        try{
            return new ResponseEntity<>(service.getOne(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
