package com.example.managespending.data.remotes.services.impl;

import com.example.managespending.data.mappers.HistoryMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.EventDTO;
import com.example.managespending.data.models.dto.HistoryDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.dto.request.GetTotalBetweenDate;
import com.example.managespending.data.models.dto.request.GetTotalEventDTO;
import com.example.managespending.data.models.dto.request.GetTotalExpenseDTO;
import com.example.managespending.data.models.dto.request.GetListDayHaveTransactionDTO;
import com.example.managespending.data.models.dto.response.BarItemDTO;
import com.example.managespending.data.models.dto.response.ListDaysHaveTransactionDTO;
import com.example.managespending.data.models.dto.response.PieItemDTO;
import com.example.managespending.data.models.dto.response.TotalCostDTO;
import com.example.managespending.data.models.entities.*;
import com.example.managespending.data.remotes.repositories.*;
import com.example.managespending.data.remotes.services.HistoryService;
import com.example.managespending.data.remotes.services.base.BaseService;
import com.example.managespending.utils.ResponseCode;
import com.example.managespending.utils.enums.ExpenseType;
import com.example.managespending.utils.enums.HistoryAction;
import com.example.managespending.utils.enums.HistoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl extends BaseService<BaseDTO> implements HistoryService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    HistoryMapper mapper;

    @Override
    public ResponseDTO<BaseDTO> create(BaseDTO baseDTO) {

        try {

            if (
                    ((HistoryDTO) baseDTO).getAccountUsername() == null ||
                            ((HistoryDTO) baseDTO).getAccountUsername().length() == 0 ||
                            ((HistoryDTO) baseDTO).getWalletId() == null ||
                            ((HistoryDTO) baseDTO).getExpense() == null ||
                            ((HistoryDTO) baseDTO).getHistoryCost().compareTo(BigDecimal.ZERO) <= 0 ||
                            ((HistoryDTO) baseDTO).getHistoryCost() == null
            ) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input all require fields !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Account account = accountRepository.findAccountByAccountUsername(((HistoryDTO) baseDTO).getAccountUsername());
            History history = mapper.mapToEntity((HistoryDTO) baseDTO, History.class);

            if (account == null) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("Account is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Optional<Wallet> walletOpt = walletRepository.findById(((HistoryDTO) baseDTO).getWalletId());

            if (!walletOpt.isPresent()) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("Wallet is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            if (((HistoryDTO) baseDTO).getHistoryCost().compareTo(walletOpt.get().getWalletBalance()) > 0) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("Wallet is not have enough money !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Optional<Expense> expenseOpt = expenseRepository.findById(((HistoryDTO) baseDTO).getExpense().getExpenseId());

            if (!expenseOpt.isPresent()) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("Expense is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            if (expenseOpt.get().getExpenseType().equals(ExpenseType.DISBURSE)) {

                walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().subtract(((HistoryDTO) baseDTO).getHistoryCost()));
                walletRepository.save(walletOpt.get());

                Budget budget = budgetRepository.findBudgetByAccountAndExpense(account, expenseOpt.get());

                if (budget != null) {

//                    return ResponseDTO.<BaseDTO>builder()
//                            .message("Budget object is not exist !")
//                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
//                            .createdTime(LocalDateTime.now())
//                            .build();
                    budget.setBudgetPresentValue(budget.getBudgetPresentValue().add(((HistoryDTO) baseDTO).getHistoryCost()));
                    budgetRepository.save(budget);

                    history.setBudget(budget);
                }

            } else {

                if (((HistoryDTO) baseDTO).getHistoryCost().compareTo(BigDecimal.ZERO) < 0) {

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Please input cost greater than 0 !")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();

                }

                walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().add(((HistoryDTO) baseDTO).getHistoryCost()));
                walletRepository.save(walletOpt.get());

            }

            if (((HistoryDTO) baseDTO).getEventId() != null && expenseOpt.get().getExpenseType().compareTo(ExpenseType.DISBURSE) == 0) {

                Optional<Event> eventOpt = eventRepository.findById(((HistoryDTO) baseDTO).getEventId());

                if (!eventOpt.isPresent()) {

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Event object is not exist !")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();

                } else if (eventOpt.get().getWallet() != null && eventOpt.get().getWallet() != walletOpt.get()) {

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Wallet is not correct !")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();

                } else {
                    history.setEvent(eventOpt.get());
                }

            }

            if (expenseOpt.get().getExpenseType().equals(ExpenseType.DISBURSE)) {
                history.setHistoryAction(HistoryAction.WITHDRAW);
                history.setHistoryType(HistoryType.DISBURSE);
            } else {
                history.setHistoryAction(HistoryAction.RECHARGE);
                history.setHistoryType(HistoryType.INCOME);
            }

//            history.setEvent(null);
            history.setAccount(account);
            history.setWallet(walletOpt.get());
            history.setExpense(expenseOpt.get());
            history.setHistoryCost(((HistoryDTO)baseDTO).getHistoryCost());

            historyRepository.save(history);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Create history complete !")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .object(mapper.mapToDTO(history, HistoryDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Create history fail !")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> delete(BaseDTO baseDTO) {

        try {

            Optional<History> historyOpt = historyRepository.findById(((HistoryDTO) baseDTO).getHistoryId());

            if (!historyOpt.isPresent()) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("History is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            if (historyOpt.get().getBudget() != null) {

                historyOpt.get().getWallet().setWalletBalance(historyOpt.get().getWallet().getWalletBalance().add(historyOpt.get().getHistoryCost()));
                walletRepository.save(historyOpt.get().getWallet());
                historyOpt.get().getBudget().setBudgetPresentValue(historyOpt.get().getBudget().getBudgetPresentValue().subtract(historyOpt.get().getHistoryCost()));
                budgetRepository.save(historyOpt.get().getBudget());

            } else {

                historyOpt.get().getWallet().setWalletBalance(historyOpt.get().getWallet().getWalletBalance().subtract(historyOpt.get().getHistoryCost()));
                walletRepository.save(historyOpt.get().getWallet());

            }

            historyRepository.delete(historyOpt.get());

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete history complete !")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {

            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete history fail !")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> update(BaseDTO baseDTO) {

        try {
            if (
                    ((HistoryDTO) baseDTO).getAccountUsername() == null ||
                            ((HistoryDTO) baseDTO).getAccountUsername().length() == 0 ||
                            ((HistoryDTO) baseDTO).getWalletId() == null ||
                            ((HistoryDTO) baseDTO).getExpense().getExpenseId() == null ||
                            ((HistoryDTO) baseDTO).getHistoryCost().compareTo(BigDecimal.ZERO) <= 0 ||
                            ((HistoryDTO) baseDTO).getHistoryCost() == null ||
                            ((HistoryDTO) baseDTO).getHistoryId() == null
            ) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input all require fields !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Account account = accountRepository.findAccountByAccountUsername(((HistoryDTO) baseDTO).getAccountUsername());
            Optional<History> historyOpt = historyRepository.findById(((HistoryDTO) baseDTO).getHistoryId());
            Optional<Wallet> walletOpt = walletRepository.findById(((HistoryDTO) baseDTO).getWalletId());
            Optional<Expense> expenseOpt = expenseRepository.findById(((HistoryDTO) baseDTO).getExpense().getExpenseId());
            Optional<Event> eventOpt = eventRepository.findById(((HistoryDTO) baseDTO).getEventId());

            if (!historyOpt.isPresent()) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("History is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            } else if (account == null) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Account is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            } else if (!walletOpt.isPresent()) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Wallet is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            } else if (!expenseOpt.isPresent()) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Expense is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            } else if (((HistoryDTO) baseDTO).getHistoryCost().compareTo(BigDecimal.ZERO) == 0) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input the cost is greater than 0 !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else if (budgetRepository.findBudgetByAccountAndExpense(account, expenseOpt.get()) == null) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Budget is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            if (eventOpt.isPresent()) {
                if (expenseOpt.get().getExpenseType().compareTo(ExpenseType.DISBURSE) == 0) {
                    Budget budget = budgetRepository.findBudgetByAccountAndExpense(account, expenseOpt.get());

                    if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() > historyOpt.get().getHistoryCost().doubleValue()) {
                        double value = ((HistoryDTO) baseDTO).getHistoryCost().doubleValue() - historyOpt.get().getHistoryCost().doubleValue();
                        if (value > walletOpt.get().getWalletBalance().doubleValue()) {
                            return ResponseDTO.<BaseDTO>builder()
                                    .message("Wallet balance is not enough money !")
                                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                                    .createdTime(LocalDateTime.now())
                                    .build();
                        } else {
                            walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().subtract(BigDecimal.valueOf(value)));
                            historyOpt.get().setWallet(walletRepository.save(walletOpt.get()));
                            budget.setBudgetPresentValue(budget.getBudgetPresentValue().add(BigDecimal.valueOf(value)));
                            historyOpt.get().setBudget(budgetRepository.save(budget));
                            historyOpt.get().setExpense(expenseOpt.get());
                            historyOpt.get().setHistoryNote(((HistoryDTO) baseDTO).getHistoryNote());
                            historyOpt.get().setHistoryAction(HistoryAction.WITHDRAW);
                            historyOpt.get().setHistoryCost(((HistoryDTO)baseDTO).getHistoryCost());
                            historyRepository.save(historyOpt.get());
                            return ResponseDTO.<BaseDTO>builder()
                                    .message("Update history complete !")
                                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                                    .object(mapper.mapToDTO(historyOpt.get(), HistoryDTO.class))
                                    .createdTime(LocalDateTime.now())
                                    .build();
                        }
                    } else if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() < historyOpt.get().getHistoryCost().doubleValue()) {
                        double value = historyOpt.get().getHistoryCost().doubleValue() - ((HistoryDTO) baseDTO).getHistoryCost().doubleValue();
                        walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().add(BigDecimal.valueOf(value)));
                        historyOpt.get().setWallet(walletRepository.save(walletOpt.get()));
                        budget.setBudgetPresentValue(budget.getBudgetPresentValue().subtract(BigDecimal.valueOf(value)));
                        historyOpt.get().setBudget(budgetRepository.save(budget));
                        historyOpt.get().setExpense(expenseOpt.get());
                        historyOpt.get().setHistoryNote(((HistoryDTO) baseDTO).getHistoryNote());
                        historyOpt.get().setHistoryAction(HistoryAction.WITHDRAW);
                        historyOpt.get().setHistoryCost(((HistoryDTO)baseDTO).getHistoryCost());
                        historyRepository.save(historyOpt.get());
                        return ResponseDTO.<BaseDTO>builder()
                                .message("Update history complete !")
                                .statusCode(ResponseCode.RESPONSE_OK_CODE)
                                .object(mapper.mapToDTO(historyOpt.get(), HistoryDTO.class))
                                .createdTime(LocalDateTime.now())
                                .build();
                    }

                }
                return ResponseDTO.<BaseDTO>builder()
                        .message("In event only accept the DISBURSE type !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
//                        if (((HistoryDTO) baseDTO).getEventId() != null && expenseOpt.get().getExpenseType().equals(ExpenseType.DISBURSE)) {
//
//                            Optional<Event> eventOpt = eventRepository.findById(((HistoryDTO) baseDTO).getEventId());
//
//                            if (!eventOpt.isPresent()) {
//
//                                return ResponseDTO.<BaseDTO>builder()
//                                        .message("Event object is not exist !")
//                                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
//                                        .createdTime(LocalDateTime.now())
//                                        .build();
//
//                            } else if (eventOpt.get().getWallet() != null && eventOpt.get().getWallet() != walletOpt.get()) {
//
//                                return ResponseDTO.<BaseDTO>builder()
//                                        .message("Wallet is not correct !")
//                                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
//                                        .createdTime(LocalDateTime.now())
//                                        .build();
//
//                            } else {
//                                historyOpt.get().setEvent(eventOpt.get());
//                            }
//
//                        } else {
//                            historyOpt.get().setEvent(null);
//                        }
//
//
//                        if (historyOpt.get().getExpense().getBudgets() != null) {
//
//                            if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() > historyOpt.get().getHistoryCost().doubleValue()) {
//                                double value = ((HistoryDTO) baseDTO).getHistoryCost().doubleValue() - historyOpt.get().getHistoryCost().doubleValue();
//                                historyOpt.get().getWallet().setWalletBalance(historyOpt.get().getWallet().getWalletBalance().subtract(BigDecimal.valueOf(value)));
//                                walletRepository.save(historyOpt.get().getWallet());
//                                historyOpt.get().getBudget().setBudgetPresentValue(historyOpt.get().getBudget().getBudgetPresentValue().add(BigDecimal.valueOf(value)));
//                                budgetRepository.save(historyOpt.get().getBudget());
//                            } else if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() < historyOpt.get().getHistoryCost().doubleValue()) {
//                                double value = historyOpt.get().getHistoryCost().doubleValue() - ((HistoryDTO) baseDTO).getHistoryCost().doubleValue();
//                                historyOpt.get().getWallet().setWalletBalance(historyOpt.get().getWallet().getWalletBalance().add(BigDecimal.valueOf(value)));
//                                walletRepository.save(historyOpt.get().getWallet());
//                                historyOpt.get().getBudget().setBudgetPresentValue(historyOpt.get().getBudget().getBudgetPresentValue().subtract(BigDecimal.valueOf(value)));
//                                budgetRepository.save(historyOpt.get().getBudget());
//                            }
//
////                historyOpt.get().getWallet().setWalletBalance(historyOpt.get().getWallet().getWalletBalance().add(historyOpt.get().getHistoryCost()));
////                walletRepository.save(historyOpt.get().getWallet());
////                historyOpt.get().getBudget().setBudgetPresentValue(historyOpt.get().getBudget().getBudgetPresentValue().subtract(historyOpt.get().getHistoryCost()));
////                budgetRepository.save(historyOpt.get().getBudget());
//
//                        } else {
//
//                            if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() > historyOpt.get().getHistoryCost().doubleValue()) {
//                                double value = ((HistoryDTO) baseDTO).getHistoryCost().doubleValue() - historyOpt.get().getHistoryCost().doubleValue();
//                                historyOpt.get().getWallet().setWalletBalance(historyOpt.get().getWallet().getWalletBalance().subtract(BigDecimal.valueOf(value)));
//                                walletRepository.save(historyOpt.get().getWallet());
//                            } else if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() < historyOpt.get().getHistoryCost().doubleValue()) {
//                                double value = historyOpt.get().getHistoryCost().doubleValue() - ((HistoryDTO) baseDTO).getHistoryCost().doubleValue();
//                                historyOpt.get().getWallet().setWalletBalance(historyOpt.get().getWallet().getWalletBalance().add(BigDecimal.valueOf(value)));
//                                walletRepository.save(historyOpt.get().getWallet());
//                            }
//
//
////                historyOpt.get().getWallet().setWalletBalance(historyOpt.get().getWallet().getWalletBalance().subtract(historyOpt.get().getHistoryCost()));
////                walletRepository.save(historyOpt.get().getWallet());
//
//                        }
//
//                        if (expenseOpt.get().getExpenseType().equals(ExpenseType.DISBURSE)) {
//
//                            double value = 0.0;
//
//                            if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() > historyOpt.get().getHistoryCost().doubleValue()) {
//                                value = ((HistoryDTO) baseDTO).getHistoryCost().doubleValue() - historyOpt.get().getHistoryCost().doubleValue();
//                            } else if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() < historyOpt.get().getHistoryCost().doubleValue()) {
//                                value = historyOpt.get().getHistoryCost().doubleValue() - ((HistoryDTO) baseDTO).getHistoryCost().doubleValue();
//                            }
//
////                if (((HistoryDTO) baseDTO).getHistoryCost().compareTo(walletOpt.get().getWalletBalance().add(historyOpt.get().getHistoryCost())) > 0) {
////
////                    return ResponseDTO.<BaseDTO>builder()
////                            .message("Wallet is not have enough money !")
////                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
////                            .createdTime(LocalDateTime.now())
////                            .build();
////
////                }
//
//                            if (value > historyOpt.get().getWallet().getWalletBalance().doubleValue()) {
//
//                                return ResponseDTO.<BaseDTO>builder()
//                                        .message("Wallet is not have enough money !")
//                                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
//                                        .createdTime(LocalDateTime.now())
//                                        .build();
//
//                            }
//
//                            walletOpt.get().setWalletBalance((walletOpt.get().getWalletBalance().subtract(BigDecimal.valueOf(value))));
//                            walletRepository.save(walletOpt.get());
//
//                            Budget budget = budgetRepository.findBudgetByAccountAndExpense(account, expenseOpt.get());
//
//                            if (budget != null) {
//                                budget.setBudgetPresentValue((budget.getBudgetPresentValue()).add(BigDecimal.valueOf(value)));
//                                budgetRepository.save(budget);
//
//                                historyOpt.get().setBudget(budget);
//                            }
//
//
////                if (budget == null) {
////
////                    return ResponseDTO.<BaseDTO>builder()
////                            .message("Budget object is not exist !")
////                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
////                            .createdTime(LocalDateTime.now())
////                            .build();
////
////                }
////
////                budget.setBudgetPresentValue((budget.getBudgetPresentValue()).add(((HistoryDTO) baseDTO).getHistoryCost()));
////                budgetRepository.save(budget);
////
////                historyOpt.get().setBudget(budget);
//
//                        } else {
//
//                            if (((HistoryDTO) baseDTO).getHistoryCost().compareTo(BigDecimal.ZERO) < 0) {
//
//                                return ResponseDTO.<BaseDTO>builder()
//                                        .message("Please input cost greater than 0 !")
//                                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
//                                        .createdTime(LocalDateTime.now())
//                                        .build();
//
//                            }
//                            double value = 0.0;
//
//                            if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() > historyOpt.get().getHistoryCost().doubleValue()) {
//                                value = ((HistoryDTO) baseDTO).getHistoryCost().doubleValue() - historyOpt.get().getHistoryCost().doubleValue();
//                                walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().subtract(BigDecimal.valueOf(value)));
//                                walletRepository.save(walletOpt.get());
//
//                                historyOpt.get().setBudget(null);
//                            } else if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() < historyOpt.get().getHistoryCost().doubleValue()) {
//                                value = historyOpt.get().getHistoryCost().doubleValue() - ((HistoryDTO) baseDTO).getHistoryCost().doubleValue();
//
//                                walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().add(BigDecimal.valueOf(value)));
//                                walletRepository.save(walletOpt.get());
//
//                                historyOpt.get().setBudget(null);
//                            }
//
////                walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().add(((HistoryDTO) baseDTO).getHistoryCost()));
////                walletRepository.save(walletOpt.get());
////
////                historyOpt.get().setBudget(null);
//
//                        }
//
//                        if (expenseOpt.get().getExpenseType().equals(ExpenseType.DISBURSE)) {
//                            historyOpt.get().setHistoryAction(HistoryAction.WITHDRAW);
//                            historyOpt.get().setHistoryType(HistoryType.DISBURSE);
//                        } else {
//                            historyOpt.get().setHistoryAction(HistoryAction.RECHARGE);
//                            historyOpt.get().setHistoryType(HistoryType.INCOME);
//                        }
//
//                        historyOpt.get().setAccount(account);
//                        historyOpt.get().setWallet(walletOpt.get());
//                        historyOpt.get().setExpense(expenseOpt.get());
//                        historyOpt.get().setHistoryCost(((HistoryDTO) baseDTO).getHistoryCost());
//                        historyOpt.get().setHistoryNote(((HistoryDTO) baseDTO).getHistoryNote());
//
//                        historyRepository.save(historyOpt.get());
//
//                        return ResponseDTO.<BaseDTO>builder()
//                                .message("Update history complete !")
//                                .statusCode(ResponseCode.RESPONSE_OK_CODE)
//                                .object(mapper.mapToDTO(historyOpt.get(), HistoryDTO.class))
//                                .createdTime(LocalDateTime.now())
//                                .build();

            }

            historyOpt.get().setEvent(null);
            if (expenseOpt.get().getExpenseType().compareTo(ExpenseType.DISBURSE) == 0) {
                Budget budget = budgetRepository.findBudgetByAccountAndExpense(account, expenseOpt.get());

                if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() > historyOpt.get().getHistoryCost().doubleValue()) {
                    double value = ((HistoryDTO) baseDTO).getHistoryCost().doubleValue() - historyOpt.get().getHistoryCost().doubleValue();
                    if (value > walletOpt.get().getWalletBalance().doubleValue()) {
                        return ResponseDTO.<BaseDTO>builder()
                                .message("Wallet balance is not enough money !")
                                .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                                .createdTime(LocalDateTime.now())
                                .build();
                    } else {
                        walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().subtract(BigDecimal.valueOf(value)));
                        historyOpt.get().setWallet(walletRepository.save(walletOpt.get()));
                        budget.setBudgetPresentValue(budget.getBudgetPresentValue().add(BigDecimal.valueOf(value)));
                        historyOpt.get().setBudget(budgetRepository.save(budget));
                        historyOpt.get().setExpense(expenseOpt.get());
                        historyOpt.get().setHistoryNote(((HistoryDTO) baseDTO).getHistoryNote());
                        historyOpt.get().setHistoryAction(HistoryAction.WITHDRAW);
                        historyOpt.get().setHistoryCost(((HistoryDTO)baseDTO).getHistoryCost());
                        historyRepository.save(historyOpt.get());
                        return ResponseDTO.<BaseDTO>builder()
                                .message("Update history complete !")
                                .statusCode(ResponseCode.RESPONSE_OK_CODE)
                                .object(mapper.mapToDTO(historyOpt.get(), HistoryDTO.class))
                                .createdTime(LocalDateTime.now())
                                .build();

                    }
                } else if (((HistoryDTO) baseDTO).getHistoryCost().doubleValue() < historyOpt.get().getHistoryCost().doubleValue()) {
                    double value = historyOpt.get().getHistoryCost().doubleValue() - ((HistoryDTO) baseDTO).getHistoryCost().doubleValue();
                    walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().add(BigDecimal.valueOf(value)));
                    historyOpt.get().setWallet(walletRepository.save(walletOpt.get()));
                    budget.setBudgetPresentValue(budget.getBudgetPresentValue().subtract(BigDecimal.valueOf(value)));
                    historyOpt.get().setBudget(budgetRepository.save(budget));
                    historyOpt.get().setExpense(expenseOpt.get());
                    historyOpt.get().setHistoryNote(((HistoryDTO) baseDTO).getHistoryNote());
                    historyOpt.get().setHistoryAction(HistoryAction.WITHDRAW);
                    historyOpt.get().setHistoryCost(((HistoryDTO)baseDTO).getHistoryCost());
                    historyRepository.save(historyOpt.get());
                    return ResponseDTO.<BaseDTO>builder()
                            .message("Update history complete !")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .object(mapper.mapToDTO(historyOpt.get(), HistoryDTO.class))
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
            walletOpt.get().setWalletBalance(walletOpt.get().getWalletBalance().add(((HistoryDTO) baseDTO).getHistoryCost()));
            historyOpt.get().setWallet(walletRepository.save(walletOpt.get()));
            historyOpt.get().setExpense(expenseOpt.get());
            historyOpt.get().setHistoryNote(((HistoryDTO) baseDTO).getHistoryNote());
            historyOpt.get().setHistoryAction(HistoryAction.RECHARGE);
            historyOpt.get().setHistoryCost(((HistoryDTO)baseDTO).getHistoryCost());
            historyRepository.save(historyOpt.get());
            return ResponseDTO.<BaseDTO>builder()
                    .message("Update history complete !")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .object(mapper.mapToDTO(historyOpt.get(), HistoryDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();


//            historyRepository.save(historyOpt.get());
//            return ResponseDTO.<BaseDTO>builder()
//                    .message("Update history complete!")
//                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
//                    .object(mapper.mapToDTO(historyOpt.get(), HistoryDTO.class))
//                    .createdTime(LocalDateTime.now())
//                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Update history fail !")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> getAllByWithdrawPieChart(BaseDTO baseDTO) {

        try {

            Account account = accountRepository.findAccountByAccountUsername(((GetTotalExpenseDTO) baseDTO).getAccountUsername());
            List<Tuple> pieChartTuple = historyRepository.getTransactionListByAccountAndHistoryAction(account.getAccountId(), HistoryAction.WITHDRAW.toString(), ((GetTotalExpenseDTO) baseDTO).getDate(), ((GetTotalExpenseDTO) baseDTO).getGetDateType().value);

            List<PieItemDTO> pieChartList = pieChartTuple.stream().map(
                    p -> new PieItemDTO(p.get("expense_name", String.class), p.get("total_cost", BigDecimal.class))
            ).collect(Collectors.toList());

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get histories complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .objectList(pieChartList)
                    .createdTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get histories fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> getAllByRechargePieChart(BaseDTO baseDTO) {

        try {

            Account account = accountRepository.findAccountByAccountUsername(((GetTotalExpenseDTO) baseDTO).getAccountUsername());

            List<Tuple> pieChartTuple = historyRepository.getTransactionListByAccountAndHistoryAction(account.getAccountId(), HistoryAction.RECHARGE.toString(), ((GetTotalExpenseDTO) baseDTO).getDate(), ((GetTotalExpenseDTO) baseDTO).getGetDateType().value);

            List<PieItemDTO> pieChartList = pieChartTuple.stream().map(
                    p -> new PieItemDTO(p.get("expense_name", String.class), p.get("total_cost", BigDecimal.class))
            ).collect(Collectors.toList());

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get histories complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .objectList(pieChartList)
                    .createdTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get histories fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> getAllByWithdrawBarChart(BaseDTO baseDTO) {

        try {

            Account account = accountRepository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());
            List<History> histories = historyRepository.findAllByAccountAndHistoryAction(account, HistoryAction.WITHDRAW);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get histories complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .objectList(mapper.mapToDTOList(histories, HistoryDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get histories fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }


    @Override
    public ResponseDTO<BaseDTO> getListTransactionByDate(BaseDTO baseDTO) {
        try {
            if (((GetTotalExpenseDTO) baseDTO).getDate() == "" ||
                    ((GetTotalExpenseDTO) baseDTO).getAccountUsername() == "" ||
                    ((GetTotalExpenseDTO) baseDTO).getGetDateType().value == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input all field !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetTotalExpenseDTO) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {

                    List<History> listTransactions = historyRepository.getListTransactionByDate(account.getAccountId(), ((GetTotalExpenseDTO) baseDTO).getDate(), HistoryAction.RECHARGE.name(), HistoryAction.WITHDRAW.name(), ((GetTotalExpenseDTO) baseDTO).getGetDateType().value);


                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get transactions complete !!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(mapper.mapToDTOList(listTransactions, HistoryDTO.class))
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get transactions fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> getListDaysHaveTransaction(BaseDTO baseDTO) {
        try {

            if (((GetListDayHaveTransactionDTO) baseDTO).getAccountUsername() == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the account_username !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else if (((GetListDayHaveTransactionDTO) baseDTO).getDate() == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter month !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetListDayHaveTransactionDTO) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {

                    List<Tuple> listItems = historyRepository.getListDaysHaveTransactionsInMonth(account.getAccountId(), ((GetListDayHaveTransactionDTO) baseDTO).getDate(), HistoryAction.WITHDRAW.name(), HistoryAction.RECHARGE.name());


                    List<ListDaysHaveTransactionDTO> listDays = listItems.stream().map(
                            p -> new ListDaysHaveTransactionDTO(p.get("history_noted_date", Date.class).toString())
                    ).collect(Collectors.toList());


                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get transactions complete!!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(listDays)
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get transactions fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> getTotalCostWithdraw(BaseDTO baseDTO) {
        try {
            if (((GetTotalExpenseDTO) baseDTO).getDate() == "" ||
                    ((GetTotalExpenseDTO) baseDTO).getAccountUsername() == "" ||
                    ((GetTotalExpenseDTO) baseDTO).getGetDateType().value == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input all field !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetTotalExpenseDTO) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {
                    Tuple items = historyRepository.getTotalCost(account.getAccountId(), ((GetTotalExpenseDTO) baseDTO).getDate(), HistoryAction.WITHDRAW.name(), ((GetTotalExpenseDTO) baseDTO).getGetDateType().value);


                    if (items.get("total_cost", BigDecimal.class) != null) {
                        double value = items.get("total_cost", BigDecimal.class).doubleValue();

                        TotalCostDTO totalCost = new TotalCostDTO();

                        totalCost.setTotalCost(BigDecimal.valueOf(value));


                        return ResponseDTO.<BaseDTO>builder()
                                .message("Get transactions complete !!!")
                                .statusCode(ResponseCode.RESPONSE_OK_CODE)
                                .object(totalCost)
                                .createdTime(LocalDateTime.now())
                                .build();
                    } else {
                        TotalCostDTO totalCost = new TotalCostDTO();

                        totalCost.setTotalCost(BigDecimal.valueOf(0.0));

                        return ResponseDTO.<BaseDTO>builder()
                                .message("Get transactions complete !!!")
                                .statusCode(ResponseCode.RESPONSE_OK_CODE)
                                .object(totalCost)
                                .createdTime(LocalDateTime.now())
                                .build();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get transactions fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> getTotalCostRecharge(BaseDTO baseDTO) {
        try {
            if (((GetTotalExpenseDTO) baseDTO).getDate() == "" ||
                    ((GetTotalExpenseDTO) baseDTO).getAccountUsername() == "" ||
                    ((GetTotalExpenseDTO) baseDTO).getGetDateType() == null) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input all field !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetTotalExpenseDTO) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {
                    Tuple items = historyRepository.getTotalCost(account.getAccountId(), ((GetTotalExpenseDTO) baseDTO).getDate(), HistoryAction.RECHARGE.name(), ((GetTotalExpenseDTO) baseDTO).getGetDateType().value);

                    if (items.get("total_cost", BigDecimal.class) != null) {
                        double value = items.get("total_cost", BigDecimal.class).doubleValue();

                        TotalCostDTO totalCost = new TotalCostDTO();

                        totalCost.setTotalCost(BigDecimal.valueOf(value));


                        return ResponseDTO.<BaseDTO>builder()
                                .message("Get transactions complete !!!")
                                .statusCode(ResponseCode.RESPONSE_OK_CODE)
                                .object(totalCost)
                                .createdTime(LocalDateTime.now())
                                .build();
                    } else {
                        TotalCostDTO totalCost = new TotalCostDTO();

                        totalCost.setTotalCost(BigDecimal.valueOf(0.0));


                        return ResponseDTO.<BaseDTO>builder()
                                .message("Get transactions complete !!!")
                                .statusCode(ResponseCode.RESPONSE_OK_CODE)
                                .object(totalCost)
                                .createdTime(LocalDateTime.now())
                                .build();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get transactions fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> getTransactionByEvent(BaseDTO baseDTO) {
        try {
            if (((GetTotalEventDTO) baseDTO).getEventId() == null || ((GetTotalEventDTO) baseDTO).getAccountUsername() == null) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter all fields !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Event event = eventRepository.findById(((GetTotalEventDTO) baseDTO).getEventId()).get();
                Account account = accountRepository.findAccountByAccountUsername(((GetTotalEventDTO) baseDTO).getAccountUsername());

                if (event == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The event doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {
                    List<History> listTransaction = historyRepository.getTransactionByEvent(account.getAccountId(), event.getEventId(), HistoryAction.RECHARGE.name(), HistoryAction.WITHDRAW.name());

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get transaction complete !!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(mapper.mapToDTOList(listTransaction, HistoryDTO.class))
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get transactions fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> getTotalCostByEvent(BaseDTO baseDTO) {
        try {
            if (((GetTotalEventDTO) baseDTO).getEventId() == null ||
                    ((GetTotalEventDTO) baseDTO).getHistoryAction().name() == null ||
                    ((GetTotalEventDTO) baseDTO).getAccountUsername() == null) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter all fields !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Event event = eventRepository.findById(((GetTotalEventDTO) baseDTO).getEventId()).get();
                Account account = accountRepository.findAccountByAccountUsername(((GetTotalEventDTO) baseDTO).getAccountUsername());

                if (event == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The event doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {
                    Tuple value = historyRepository.getTotalCostByEvent(account.getAccountId(), event.getEventId(), ((GetTotalEventDTO) baseDTO).getHistoryAction().name());

                    TotalCostDTO totalCostDTO = new TotalCostDTO();

                    totalCostDTO.setTotalCost(value.get("total_cost", BigDecimal.class));

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get total_cost_by_event complete !!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .object(totalCostDTO)
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get total_cost fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> getListDayHaveTransactionByEvent(BaseDTO baseDTO) {
        try {

            if (((GetListDayHaveTransactionDTO) baseDTO).getAccountUsername() == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the account_username !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else if (((GetListDayHaveTransactionDTO) baseDTO).getDate() == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter month !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else if (((GetListDayHaveTransactionDTO) baseDTO).getEventId() == null) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter event_id !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetListDayHaveTransactionDTO) baseDTO).getAccountUsername());
                Event event = eventRepository.findById(((GetListDayHaveTransactionDTO) baseDTO).getEventId()).get();
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else if (event == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The event doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {
                    List<Tuple> listItems = historyRepository.getListDayHaveTransactionByEvent(account.getAccountId(), event.getEventId(), ((GetListDayHaveTransactionDTO) baseDTO).getDate(), HistoryAction.WITHDRAW.name(), HistoryAction.RECHARGE.name());

                    List<ListDaysHaveTransactionDTO> listDays = listItems.stream().map(
                            p -> new ListDaysHaveTransactionDTO(p.get("history_noted_date", Date.class).toString())
                    ).collect(Collectors.toList());


                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get list day have transaction complete!!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(listDays)
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get list day have transaction fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> getBarItemMonth(BaseDTO baseDTO) {
        try {
            if (((GetTotalBetweenDate) baseDTO).getAccountUsername() == null ||
                    ((GetTotalBetweenDate) baseDTO).getYear() == null ||
                    ((GetTotalBetweenDate) baseDTO).getLastDayOfMonth() == null ||
                    ((GetTotalBetweenDate) baseDTO).getMonth() == null) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter all fields !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetTotalBetweenDate) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {

                    List<String> week1 = new ArrayList<>();
                    List<String> week2 = new ArrayList<>();
                    List<String> week3 = new ArrayList<>();
                    List<String> week4 = new ArrayList<>();

                    week1.add(((GetTotalBetweenDate) baseDTO).getYear() + "-" + ((GetTotalBetweenDate) baseDTO).getMonth() + "-" + "01");
                    week1.add(((GetTotalBetweenDate) baseDTO).getYear() + "-" + ((GetTotalBetweenDate) baseDTO).getMonth() + "-" + "07");

                    week2.add(((GetTotalBetweenDate) baseDTO).getYear() + "-" + ((GetTotalBetweenDate) baseDTO).getMonth() + "-" + "08");
                    week2.add(((GetTotalBetweenDate) baseDTO).getYear() + "-" + ((GetTotalBetweenDate) baseDTO).getMonth() + "-" + "14");

                    week3.add(((GetTotalBetweenDate) baseDTO).getYear() + "-" + ((GetTotalBetweenDate) baseDTO).getMonth() + "-" + "15");
                    week3.add(((GetTotalBetweenDate) baseDTO).getYear() + "-" + ((GetTotalBetweenDate) baseDTO).getMonth() + "-" + "21");

                    week4.add(((GetTotalBetweenDate) baseDTO).getYear() + "-" + ((GetTotalBetweenDate) baseDTO).getMonth() + "-" + "22");
                    week4.add(((GetTotalBetweenDate) baseDTO).getYear() + "-" + ((GetTotalBetweenDate) baseDTO).getMonth() + "-" + ((GetTotalBetweenDate) baseDTO).getLastDayOfMonth());


                    Tuple valueW1 = historyRepository.getTotalCostBetweenDate(account.getAccountId(), week1.get(0), week1.get(1), ((GetTotalBetweenDate) baseDTO).getHistoryAction().name());
                    Tuple valueW2 = historyRepository.getTotalCostBetweenDate(account.getAccountId(), week2.get(0), week2.get(1), ((GetTotalBetweenDate) baseDTO).getHistoryAction().name());
                    Tuple valueW3 = historyRepository.getTotalCostBetweenDate(account.getAccountId(), week3.get(0), week3.get(1), ((GetTotalBetweenDate) baseDTO).getHistoryAction().name());
                    Tuple valueW4 = historyRepository.getTotalCostBetweenDate(account.getAccountId(), week4.get(0), week4.get(1), ((GetTotalBetweenDate) baseDTO).getHistoryAction().name());

                    BarItemDTO item1 = new BarItemDTO();
                    item1.setTotalCost((valueW1.get("total_cost", BigDecimal.class) != null) ? valueW1.get("total_cost", BigDecimal.class) : BigDecimal.valueOf(0.0));
                    item1.setDateType("Week 1");

                    BarItemDTO item2 = new BarItemDTO();
                    item2.setTotalCost((valueW2.get("total_cost", BigDecimal.class) != null) ? valueW2.get("total_cost", BigDecimal.class) : BigDecimal.valueOf(0.0));
                    item2.setDateType("Week 2");

                    BarItemDTO item3 = new BarItemDTO();
                    item3.setTotalCost((valueW3.get("total_cost", BigDecimal.class) != null) ? valueW3.get("total_cost", BigDecimal.class) : BigDecimal.valueOf(0.0));
                    item3.setDateType("Week 3");

                    BarItemDTO item4 = new BarItemDTO();
                    item4.setTotalCost((valueW4.get("total_cost", BigDecimal.class) != null) ? valueW4.get("total_cost", BigDecimal.class) : BigDecimal.valueOf(0.0));
                    item4.setDateType("Week 4");

                    List<BarItemDTO> listData = new ArrayList<>();
                    listData.add(item1);
                    listData.add(item2);
                    listData.add(item3);
                    listData.add(item4);

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get total_cost complete !!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(listData)
                            .createdTime(LocalDateTime.now())
                            .build();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get the total_cost fail !!!")
                    .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }


}
