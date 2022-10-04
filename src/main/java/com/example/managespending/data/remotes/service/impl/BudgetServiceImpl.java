package com.example.managespending.data.remotes.service.impl;

import com.example.managespending.data.mapper.BudgetMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.BudgetDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Budget;
import com.example.managespending.data.models.entities.Expense;
import com.example.managespending.data.models.entities.History;
import com.example.managespending.data.remotes.repositories.*;
import com.example.managespending.data.remotes.service.BudgetService;
import com.example.managespending.data.remotes.service.base.BaseService;
import com.example.managespending.utils.ResponseCode;
import com.example.managespending.utils.enums.HistoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl extends BaseService<BaseDTO> implements BudgetService {

    @Autowired
    BudgetMapper mapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public ResponseDTO<BaseDTO> create(BaseDTO baseDTO) {

        try{

            if(((BudgetDTO) baseDTO).getBudgetName().length() == 0 ||
                    ((BudgetDTO) baseDTO).getBudgetName() == null ||
                    ((BudgetDTO) baseDTO).getBudgetValue() == null ||
                    ((BudgetDTO) baseDTO).getBudgetValue().compareTo(BigDecimal.ZERO) == 0 ||
                    ((BudgetDTO) baseDTO).getBudgetMothYear().length() == 0 ||
                    ((BudgetDTO) baseDTO).getBudgetValue() == null ||
                    ((BudgetDTO) baseDTO).getExpense() == null
                    ){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please in put full budget information !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Account account = accountRepository.findAccountByAccountUsername(((BudgetDTO) baseDTO).getAccount().getAccountUsername());
            Optional<Expense> expenseOtp = expenseRepository.findById(((BudgetDTO) baseDTO).getExpense().getExpenseId());

            if(budgetRepository.findBudgetByAccountAndBudgetName(account, ((BudgetDTO) baseDTO).getBudgetName()) != null){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Budget name is exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            if(!expenseOtp.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Expense is not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            String[] str = ((BudgetDTO) baseDTO).getBudgetMothYear().split("-");
            if(YearMonth.of(Integer.parseInt(str[0]), Integer.parseInt(str[1])).compareTo(YearMonth.now()) < 0){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input again month and year of budget !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Budget budget = mapper.mapToEntity((BudgetDTO) baseDTO, Budget.class);
            budget.setBudgetStatus(false);
            budget.setBudgetPresentValue(BigDecimal.ZERO);
            budget.setAccount(account);
            budget.setExpense(expenseOtp.get());
            budgetRepository.save(budget);

            History history = new History();
            history.setHistoryType(HistoryType.CREATE);
            history.setHistoryNote("Create new budget name " + budget.getBudgetName());
            history.setHistoryCost(budget.getBudgetValue());
            history.setBudget(budget);
            history.setAccount(account);
            historyRepository.save(history);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Create budget complete !!!")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .object(mapper.mapToDTO(budget, BudgetDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Create budget fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> update(BaseDTO baseDTO) {

        try{

            if(((BudgetDTO) baseDTO).getBudgetName().length() == 0 ||
                    ((BudgetDTO) baseDTO).getBudgetName() == null ||
                    ((BudgetDTO) baseDTO).getBudgetValue() == null ||
                    ((BudgetDTO) baseDTO).getBudgetValue().compareTo(BigDecimal.ZERO) == 0 ||
                    ((BudgetDTO) baseDTO).getBudgetMothYear().length() == 0 ||
                    ((BudgetDTO) baseDTO).getBudgetValue() == null ||
                    ((BudgetDTO) baseDTO).getExpense() == null ||
                    ((BudgetDTO) baseDTO).getBudgetId() == null
            ){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please in put full budget information !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Optional<Budget> budgetOpt = budgetRepository.findById(((BudgetDTO) baseDTO).getBudgetId());

            if(!budgetOpt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Budget is not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Budget budget = budgetOpt.get();
            Account account = accountRepository.findAccountByAccountUsername(((BudgetDTO) baseDTO).getAccount().getAccountUsername());
            Optional<Expense> expenseOtp = expenseRepository.findById(((BudgetDTO) baseDTO).getExpense().getExpenseId());

            if(budgetRepository.findBudgetByAccountAndBudgetName(account, ((BudgetDTO) baseDTO).getBudgetName()) != null){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Budget name is exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            if(!expenseOtp.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Expense is not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            String[] str = ((BudgetDTO) baseDTO).getBudgetMothYear().split("-");
            if(YearMonth.of(Integer.parseInt(str[0]), Integer.parseInt(str[1])).compareTo(YearMonth.now()) < 0){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input again month and year of budget !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            budget.setBudgetMothYear(((BudgetDTO) baseDTO).getBudgetMothYear());
            budget.setBudgetName(((BudgetDTO) baseDTO).getBudgetName());
            budget.setBudgetValue(((BudgetDTO) baseDTO).getBudgetValue());
            budget.setExpense(expenseOtp.get());
            budget.setBudgetIcon(((BudgetDTO) baseDTO).getBudgetIcon());
            budgetRepository.save(budget);

            History history = new History();
            history.setHistoryType(HistoryType.UPDATE);
            history.setHistoryNote("Update budget name " + budget.getBudgetName());
            history.setHistoryCost(budget.getBudgetValue());
            history.setBudget(budget);
            history.setAccount(account);
            historyRepository.save(history);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Update budget complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .object(mapper.mapToDTO(budget, BudgetDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Update budget fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> delete(BaseDTO baseDTO) {

        try{

            Optional<Budget> budgetOpt = budgetRepository.findById(((BudgetDTO) baseDTO).getBudgetId());

            if(!budgetOpt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Budget is not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Budget budget = budgetOpt.get();
            Account account = accountRepository.findAccountByAccountUsername(((BudgetDTO) baseDTO).getAccount().getAccountUsername());

            History history = new History();
            history.setHistoryType(HistoryType.REMOVE);
            history.setHistoryNote("Remove budget name " + budget.getBudgetName());
            history.setHistoryCost(budget.getBudgetValue());
            history.setBudget(budget);
            history.setAccount(account);
            historyRepository.save(history);

            budgetRepository.delete(budget);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete budget complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete budget fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> getOne(BaseDTO baseDTO) {

        try{

            Optional<Budget> opt = budgetRepository.findById(((BudgetDTO) baseDTO).getBudgetId());

            if(!opt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Budget object is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Budget budget = opt.get();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get budget complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .object(mapper.mapToDTO(budget, BudgetDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e) {

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get budget fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> getAll(BaseDTO baseDTO) {

        try{

            Account account = accountRepository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());
            List<Budget> budgets = budgetRepository.findAllByAccount(account);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get budgets complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .objectList(mapper.mapToDTOList(budgets, BudgetDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get budgets fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

}
