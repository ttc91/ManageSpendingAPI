package com.example.managespending.data.remotes.service.impl;

import com.example.managespending.data.mapper.ExpenseMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.ExpenseDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Expense;
import com.example.managespending.data.models.entities.History;
import com.example.managespending.data.remotes.repositories.AccountRepository;
import com.example.managespending.data.remotes.repositories.ExpenseRepository;
import com.example.managespending.data.remotes.repositories.HistoryRepository;
import com.example.managespending.data.remotes.service.ExpenseService;
import com.example.managespending.data.remotes.service.base.BaseService;
import com.example.managespending.utils.ResponseCode;
import com.example.managespending.utils.enums.HistoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl extends BaseService<BaseDTO> implements ExpenseService {

    @Autowired
    ExpenseMapper mapper;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public ResponseDTO<BaseDTO> create(BaseDTO baseDTO) {

        try{

            if(((ExpenseDTO) baseDTO).getExpenseName().length() == 0 ||
                    ((ExpenseDTO) baseDTO).getExpenseIcon().length() == 0 ||
                    ((ExpenseDTO) baseDTO).getAccount() == null){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input name or icon or account for expense entity !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Expense expense = mapper.mapToEntity((ExpenseDTO) baseDTO, Expense.class);

            List<History> histories = new ArrayList<>();

            History history = new History();
            history.setAccount(accountRepository.findAccountByAccountUsername(((ExpenseDTO) baseDTO).getAccount().getAccountUsername()));
            history.setHistoryType(HistoryType.CREATE);
            history.setHistoryNote("Created new expense name " + expense.getExpenseName());
            histories.add(history);

            expense.setHistories(histories);
            expense.setAccount(accountRepository.findAccountByAccountUsername(((ExpenseDTO) baseDTO).getAccount().getAccountUsername()));

            expenseRepository.save(expense);

            history.setExpense(expense);
            historyRepository.save(history);


            return ResponseDTO.<BaseDTO>builder()
                    .message("Create expense complete !!!")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(expense, ExpenseDTO.class))
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Create expense fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }
    }

    @Override
    public ResponseDTO<BaseDTO> update(BaseDTO baseDTO) {

        try{

            if(((ExpenseDTO) baseDTO).getExpenseName().length() == 0 ||
                    ((ExpenseDTO) baseDTO).getExpenseIcon().length() == 0 ||
                    ((ExpenseDTO) baseDTO).getAccount() == null){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input name or icon or account for expense entity !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Optional<Expense> opt = expenseRepository.findById(((ExpenseDTO) baseDTO).getExpenseId());

            if(!opt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Expense object is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Expense expense = opt.get();

            List<History> histories = new ArrayList<>();

            History history = new History();
            history.setAccount(accountRepository.findAccountByAccountUsername(((ExpenseDTO) baseDTO).getAccount().getAccountUsername()));
            history.setHistoryType(HistoryType.UPDATE);
            history.setHistoryNote("Updated expense name " + expense.getExpenseName());
            histories.add(history);

            expense.setHistories(histories);
            expense.setExpenseType(((ExpenseDTO) baseDTO).getExpenseType());
            expense.setExpenseName(((ExpenseDTO) baseDTO).getExpenseName());
            expense.setExpenseIcon(((ExpenseDTO) baseDTO).getExpenseIcon());
            expense.setAccount(accountRepository.findAccountByAccountUsername(((ExpenseDTO) baseDTO).getAccount().getAccountUsername()));

            expenseRepository.save(expense);

            history.setExpense(expense);
            historyRepository.save(history);


            return ResponseDTO.<BaseDTO>builder()
                    .message("Update expense complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(expense, ExpenseDTO.class))
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Update expense fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> delete(BaseDTO baseDTO) {

        try{

            History history = new History();
            history.setAccount(accountRepository.findAccountByAccountUsername(((ExpenseDTO) baseDTO).getAccount().getAccountUsername()));
            history.setHistoryType(HistoryType.REMOVE);
            history.setHistoryNote("Deleted expense name " + ((ExpenseDTO) baseDTO).getExpenseName());

            Optional<Expense> opt = expenseRepository.findById(((ExpenseDTO) baseDTO).getExpenseId());

            if(!opt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Expense object is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Expense expense = opt.get();

            expenseRepository.delete(expense);
            historyRepository.save(history);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete expense complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete expense fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> getOne(BaseDTO baseDTO) {

        try{

            Optional<Expense> opt = expenseRepository.findById(((ExpenseDTO) baseDTO).getExpenseId());

            if(!opt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Expense object is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Expense expense = opt.get();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get expense complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .object(mapper.mapToDTO(expense, ExpenseDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get expense fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> getAll(BaseDTO baseDTO) {

        try{

            Account account = accountRepository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());
            List<Expense> expenses = expenseRepository.findAllByAccount(account);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get expenses complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .objectList(mapper.mapToDTOList(expenses, ExpenseDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get expenses fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

}
