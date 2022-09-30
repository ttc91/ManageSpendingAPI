package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.request.transaction.TransactionCreateDto;
import com.example.managespending.data.models.dto.request.transaction.TransactionDeleteDto;
import com.example.managespending.data.models.dto.request.transaction.TransactionUpdateDto;
import com.example.managespending.data.models.entities.RAP;
import com.example.managespending.data.models.entities.Transaction;
import com.example.managespending.data.models.entities.Wallet;
import com.example.managespending.data.remotes.service.EventService;
import com.example.managespending.data.remotes.service.RAPService;
import com.example.managespending.data.remotes.service.TransactionService;
import com.example.managespending.data.remotes.service.WalletService;
import com.example.managespending.utils.PathApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(PathApi.TRANSACTION_DOMAIN)
public class TransactionController {

//    @Autowired
//    private TransactionService transactionService;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private RAPService rapService;
//
//    @Autowired
//    private WalletService walletService;
//
//    @Autowired
//    private EventService eventService;
//
//    @PostMapping(PathApi.MODEL_INSERT_DOMAIN)
//    public ResponseEntity<Transaction> insert(@RequestBody TransactionCreateDto request) {
//        Optional<Wallet> wallet = walletService.findById(new WalletKey(request.getAccountId(), request.getWalletName()));
//        RAP rap = rapService.findRAPByRapName(request.getRapName());
//        BigDecimal value = request.getTransactionValue();
//        if (wallet.isPresent() && rap != null && value != null) {
//            Transaction transaction = modelMapper.map(request, Transaction.class);
//            transactionService.save(transaction);
//            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping(PathApi.MODEL_UPDATE_DOMAIN)
//    public ResponseEntity<Transaction> update(@RequestBody TransactionUpdateDto request) {
//        Optional<Transaction> otp = transactionService.findById(request.getTransactionId());
//        if (otp.isPresent()) {
//            Transaction transaction = otp.get();
//            transaction.setTransactionNote(request.getNewTransactionNote());
//            transaction.setTransactionValue(request.getNewTransactionValue());
//            transaction.setEvent(eventService.findEventByEventName(request.getNewEventName()));
//            transaction.setWallet(walletService.findById(new WalletKey(request.getNewAccountId(), request.getNewWalletName())).get());
//            transactionService.save(transaction);
//            return new ResponseEntity<>(transaction, HttpStatus.UPGRADE_REQUIRED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @DeleteMapping(PathApi.MODEL_DELETE_DOMAIN)
//    public ResponseEntity<Transaction> delete(@RequestBody TransactionDeleteDto request) {
//        Optional<Transaction> otp = transactionService.findById(request.getTransactionId());
//        if (otp.isPresent()) {
//            transactionService.delete(otp.get());
//            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @GetMapping(PathApi.MODEL_GETBYID_DOMAIN + "/{transactionID}")
//    public ResponseEntity<Transaction> getTransactionByID(@PathVariable("transactionID") long id) {
//        Transaction transaction = transactionService.findById(id).orElse(null);
//        if (transaction != null)
//            return new ResponseEntity<>(transaction, HttpStatus.FOUND);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping(PathApi.MODEL_GETLIST_DOMAIN)
//    public ResponseEntity<List<Transaction>> getAllTransaction() {
//        List<Transaction> list = transactionService.findAll();
//        if (list.isEmpty() == false)
//            return new ResponseEntity<>(list, HttpStatus.FOUND);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
