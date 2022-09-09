package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.req.budget.BudgetCreateDto;
import com.example.managespending.data.models.dto.req.budget.BudgetDeleteDto;
import com.example.managespending.data.models.dto.req.budget.BudgetUpdateDto;
import com.example.managespending.data.models.entities.Budget;
import com.example.managespending.data.models.entities.key.WalletKey;
import com.example.managespending.data.remotes.service.BudgetService;
import com.example.managespending.data.remotes.service.RAPService;
import com.example.managespending.data.remotes.service.WalletService;
import com.example.managespending.utils.PathApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(PathApi.BUDGET_DOMAIN)
public class BudgetController {

    @Autowired
    private BudgetService budgetService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RAPService rapService;

    @Autowired
    private WalletService walletService;

    @PostMapping(PathApi.MODEL_INSERT_DOMAIN)
    public ResponseEntity<Budget> insert(@RequestBody BudgetCreateDto request) {
        Budget budget = budgetService.findBudgetByRAPName(request.getRapName());
        if (budget == null) {
            Budget object = modelMapper.map(request, Budget.class);
            budgetService.save(object);
            return new ResponseEntity<>(object, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(PathApi.MODEL_UPDATE_DOMAIN)
    public ResponseEntity<Budget> update(@RequestBody BudgetUpdateDto request) {
        Budget budget = budgetService.findBudgetByRAPName(request.getOldRapName());
        if (budget != null) {
            budget.setBudgetValue(request.getNewBudgetValue());
            budget.setWallet(walletService.findById(new WalletKey(request.getAccountId(), request.getWalletName())).get());
            budget.setReceiptsAndPayments(rapService.findRAPByRapName(request.getNewRapName()));
            budgetService.save(budget);
            return new ResponseEntity<>(budget, HttpStatus.UPGRADE_REQUIRED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(PathApi.MODEL_DELETE_DOMAIN)
    public ResponseEntity<Budget> delete(@RequestBody BudgetDeleteDto request) {
        Budget budget = budgetService.findBudgetByRAPName(request.getRapName());
        if (budget != null) {
            budgetService.delete(budget);
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(PathApi.MODEL_GETBYNAME_DOMAIN + "/{budgetName}")
    public ResponseEntity<Budget> getBudgetByName(@PathVariable("budgetName") String name) {
        Budget budget = budgetService.findBudgetByRAPName(name);
        if (budget != null)
            return new ResponseEntity<>(budget, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(PathApi.MODEL_GETLIST_DOMAIN)
    public ResponseEntity<List<Budget>> getAllBudget() {
        List<Budget> list = budgetService.findAll();
        if (list.isEmpty() == false)
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(PathApi.MODEL_GETBYID_DOMAIN + "/{budgetID}")
    public ResponseEntity<Budget> getBudgetByID(@PathVariable("bugdetID") long id) {
        Budget budget = budgetService.findById(id).orElse(null);
        if (budget != null)
            return new ResponseEntity<>(budget, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
