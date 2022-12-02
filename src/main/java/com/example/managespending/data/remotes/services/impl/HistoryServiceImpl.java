package com.example.managespending.data.remotes.services.impl;

import com.example.managespending.data.mappers.HistoryMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.HistoryDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.dto.request.GetTotalExpenseDTO;
import com.example.managespending.data.models.dto.request.GetTransactionListByDayDTO;
import com.example.managespending.data.models.dto.request.GetTransactionListByMonthDTO;
import com.example.managespending.data.models.dto.request.GetTransactionListByWeekDTO;
import com.example.managespending.data.models.dto.response.ListDaysHaveTransactionsInMonthDTO;
import com.example.managespending.data.models.dto.response.PieItemDTO;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
            history.setAccount(account);
            history.setWallet(walletOpt.get());
            history.setExpense(expenseOpt.get());

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

            if (!historyOpt.isPresent()) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("History is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

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

            Optional<Expense> expenseOpt = expenseRepository.findById(((HistoryDTO) baseDTO).getExpense().getExpenseId());

            if (!expenseOpt.isPresent()) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("Expense is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

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
                    historyOpt.get().setEvent(eventOpt.get());
                }

            } else {
                historyOpt.get().setEvent(null);
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

            if (expenseOpt.get().getExpenseType().equals(ExpenseType.DISBURSE)) {

                if (((HistoryDTO) baseDTO).getHistoryCost().compareTo(walletOpt.get().getWalletBalance().add(historyOpt.get().getHistoryCost())) > 0) {

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Wallet is not have enough money !")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();

                }

                walletOpt.get().setWalletBalance((walletOpt.get().getWalletBalance().subtract(((HistoryDTO) baseDTO).getHistoryCost())));
                walletRepository.save(walletOpt.get());

                Budget budget = budgetRepository.findBudgetByAccountAndExpense(account, expenseOpt.get());

                if (budget == null) {

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Budget object is not exist !")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();

                }

                budget.setBudgetPresentValue((budget.getBudgetPresentValue()).add(((HistoryDTO) baseDTO).getHistoryCost()));
                budgetRepository.save(budget);

                historyOpt.get().setBudget(budget);

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

                historyOpt.get().setBudget(null);

            }

            if (expenseOpt.get().getExpenseType().equals(ExpenseType.DISBURSE)) {
                historyOpt.get().setHistoryAction(HistoryAction.WITHDRAW);
                historyOpt.get().setHistoryType(HistoryType.DISBURSE);
            } else {
                historyOpt.get().setHistoryAction(HistoryAction.RECHARGE);
                historyOpt.get().setHistoryType(HistoryType.INCOME);
            }

            historyOpt.get().setAccount(account);
            historyOpt.get().setWallet(walletOpt.get());
            historyOpt.get().setExpense(expenseOpt.get());
            historyOpt.get().setHistoryCost(((HistoryDTO) baseDTO).getHistoryCost());
            historyOpt.get().setHistoryNote(((HistoryDTO) baseDTO).getHistoryNote());

            historyRepository.save(historyOpt.get());

            return ResponseDTO.<BaseDTO>builder()
                    .message("Update history complete !")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .object(mapper.mapToDTO(historyOpt.get(), HistoryDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

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
    public ResponseDTO<BaseDTO> getTransactionByWeek(BaseDTO baseDTO) {
        try {

            if (((GetTransactionListByWeekDTO) baseDTO).getAccountUsername() == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the account_username !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else if (((GetTransactionListByWeekDTO) baseDTO).getStartDate() == null || ((GetTransactionListByWeekDTO) baseDTO).getEndDate() == null) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the startDate and endDate !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetTransactionListByWeekDTO) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                    List<History> listItems = historyRepository.getTransactionListByWeek(account.getAccountId(), df.parse(((GetTransactionListByWeekDTO) baseDTO).getStartDate()), df.parse(((GetTransactionListByWeekDTO) baseDTO).getEndDate()),HistoryAction.WITHDRAW.name(), HistoryAction.RECHARGE.name());

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get transactions complete!!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(mapper.mapToDTOList(listItems, HistoryDTO.class))
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }

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
    public ResponseDTO<BaseDTO> getTransactionByMonth(BaseDTO baseDTO) {
        try {

            if (((GetTransactionListByMonthDTO) baseDTO).getAccountUsername() == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the account_username !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else if (((GetTransactionListByMonthDTO) baseDTO).getMonth() == "" ) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter month !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetTransactionListByMonthDTO) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {

                    List<History> listItems = historyRepository.getTransactionListByMonth(account.getAccountId(), ((GetTransactionListByMonthDTO) baseDTO).getMonth(),HistoryAction.WITHDRAW.name(), HistoryAction.RECHARGE.name());

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get transactions complete!!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(mapper.mapToDTOList(listItems, HistoryDTO.class))
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
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
    public ResponseDTO<BaseDTO> getTransactionByDay(BaseDTO baseDTO) {
        try {

            if (((GetTransactionListByDayDTO) baseDTO).getAccountUsername() == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the account_username !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else if (((GetTransactionListByDayDTO) baseDTO).getDay() == "" ) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter month !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetTransactionListByDayDTO) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {

                    List<History> listItems = historyRepository.getTransactionListByDay(account.getAccountId(), ((GetTransactionListByDayDTO) baseDTO).getDay(),HistoryAction.WITHDRAW.name(), HistoryAction.RECHARGE.name());

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get transactions complete!!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(mapper.mapToDTOList(listItems, HistoryDTO.class))
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
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
    public ResponseDTO<BaseDTO> getListDaysHaveTransaction(BaseDTO baseDTO) {
        try {

            if (((GetTransactionListByMonthDTO) baseDTO).getAccountUsername() == "") {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the account_username !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else if (((GetTransactionListByMonthDTO) baseDTO).getMonth() == "" ) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter month !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            } else {
                Account account = accountRepository.findAccountByAccountUsername(((GetTransactionListByMonthDTO) baseDTO).getAccountUsername());
                if (account == null) {
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                            .createdTime(LocalDateTime.now())
                            .build();
                } else {

                    List<Tuple> listItems = historyRepository.getListDaysHaveTransactionsInMonth(account.getAccountId(), ((GetTransactionListByMonthDTO) baseDTO).getMonth(),HistoryAction.WITHDRAW.name(), HistoryAction.RECHARGE.name());

                    List<ListDaysHaveTransactionsInMonthDTO> listDays = listItems.stream().map(
                            p -> new ListDaysHaveTransactionsInMonthDTO(p.get("history_noted_date", Date.class).toString())
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

}
