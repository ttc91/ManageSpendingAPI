package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.req.wallet.WalletDeleteDto;
import com.example.managespending.data.models.dto.req.wallet.WalletCreateDto;
import com.example.managespending.data.models.dto.req.wallet.WalletUpdateDto;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Wallet;
import com.example.managespending.data.models.entities.key.WalletKey;
import com.example.managespending.data.remotes.service.AccountService;
import com.example.managespending.data.remotes.service.WalletCategoryService;
import com.example.managespending.data.remotes.service.WalletService;
import com.example.managespending.utils.PathApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(PathApi.WALLET_DOMAIN)
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletCategoryService walletCategoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(PathApi.MODEL_INSERT_DOMAIN)
    public ResponseEntity<Wallet> insert (@RequestBody WalletCreateDto request){

        Optional<Account> opt = accountService.findById(request.getUserId());
        if(opt.isPresent() && walletCategoryService.findWalletCategoryByWalletCategoryName(request.getWalletCategoryName()) != null){
            WalletKey key = new WalletKey(request.getUserId(), request.getWalletName());
            Wallet wallet = new Wallet();
            wallet.setBalance(request.getBalance());
            wallet.setId(key);
            wallet.setWalletCategory(walletCategoryService.findWalletCategoryByWalletCategoryName(request.getWalletCategoryName()));
            walletService.save(wallet);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PutMapping(PathApi.MODEL_UPDATE_DOMAIN)
    public ResponseEntity<Wallet> update (@RequestBody WalletUpdateDto request){

        System.out.println(request.getAccountId());
        Optional<Account> opt = accountService.findById(request.getAccountId());
        if(opt.isPresent() && walletCategoryService.findWalletCategoryByWalletCategoryName(request.getWalletCategoryName()) != null){

            WalletKey key = new WalletKey(request.getAccountId(), request.getOldWalletName());

            Optional<Wallet> walletOpt = walletService.findById(key);
            if(walletOpt.isPresent()){

                Wallet wallet = walletOpt.get();
                walletService.delete(wallet);

                WalletKey newWalletKey = new WalletKey(request.getAccountId(), request.getNewWalletName());
                wallet.setBalance(request.getBalance());
                wallet.setId(newWalletKey);
                wallet.setWalletCategory(walletCategoryService.findWalletCategoryByWalletCategoryName(request.getWalletCategoryName()));

                walletService.save(wallet);

                return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(PathApi.MODEL_DELETE_DOMAIN)
    public ResponseEntity<Wallet> delete(@RequestBody WalletDeleteDto request){

        try{
            walletService.deleteById(new WalletKey(request.getAccountId(), request.getWalletName()));
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
