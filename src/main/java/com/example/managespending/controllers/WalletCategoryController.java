package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.req.category.wallet.WalletCategoryDto;
import com.example.managespending.data.models.dto.req.category.wallet.WalletCategoryUpdateDto;
import com.example.managespending.data.models.entities.WalletCategory;
import com.example.managespending.data.remotes.service.WalletCategoryService;
import com.example.managespending.utils.PathApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(PathApi.WALLET_CATEGORY_DOMAIN)
public class WalletCategoryController {

    @Autowired
    private WalletCategoryService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(PathApi.MODEL_INSERT_DOMAIN)
    public ResponseEntity<WalletCategory> insert(@RequestBody WalletCategoryDto request){

        try{
            WalletCategory walletCategory = modelMapper.map(request, WalletCategory.class);
            service.save(walletCategory);
            return new ResponseEntity<>(walletCategory, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(PathApi.MODEL_UPDATE_DOMAIN)
    public ResponseEntity<WalletCategory> update(@RequestBody WalletCategoryUpdateDto request){

            WalletCategory walletCategory = service.findWalletCategoryByWalletCategoryName(request.getOldWalletCategoryName());
            if(walletCategory != null){
                walletCategory.setWalletCategoryName(request.getNewWalletCategoryName());
                service.save(walletCategory);
                return new ResponseEntity<>(walletCategory, HttpStatus.UPGRADE_REQUIRED);
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

    }

    @DeleteMapping(PathApi.MODEL_DELETE_DOMAIN)
    public ResponseEntity<WalletCategory> delete(@RequestBody WalletCategoryDto request){

        WalletCategory walletCategory = service.findWalletCategoryByWalletCategoryName(request.getWalletCategoryName());
        if(walletCategory != null){
            service.delete(walletCategory);
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
