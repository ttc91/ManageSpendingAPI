package com.example.managespending;

import com.example.managespending.data.models.entities.*;
import com.example.managespending.data.remotes.repositories.*;
import com.example.managespending.utils.enums.HistoryAction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ManagespendingApplicationTests {

    @Autowired
    private GoalRepository repo;

    @Autowired
    private AccountRepository accRepo;

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private BudgetRepository budgetRepo;


    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private HistoryRepository historyRepo;


    @Test
    void contextLoads() throws ParseException {

        Account acc = accRepo.findAccountByAccountUsername("phuc");

        List<Goal> list = repo.findGoalsByAccountAndGoalStatus(acc, false);

        List<Event> eve = eventRepo.findEventsByAccountAndEventStatus(acc, false);

        List<Budget> budget = budgetRepo.findBudgetsByAccountAndBudgetStatus(acc, false);

        List<Expense> listExpense = expenseRepo.findAllByAccount(acc);
        List<Expense> listExpense2 = expenseRepo.findAllByIsExpenseSystem(false);

        listExpense.addAll(listExpense2);

        Expense ex = expenseRepo.findById(1L).get();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df_2 = new SimpleDateFormat("yyyy-MM");

        Date startDate = df.parse("2022-11-29");
        Date endDate = df.parse("2022-12-02");
//
//        String startDate ="2022-11-29";
//        String endDate = "2022-12-02";


        Date monthFormat = df_2.parse("2022-11");



        List<History> listItems = historyRepo.getTransactionListByWeek(1L,startDate, endDate, HistoryAction.WITHDRAW.name(),HistoryAction.RECHARGE.name());
        List<History> listItems_2 = historyRepo.getTransactionListByMonth(1L, "2022-12",HistoryAction.WITHDRAW.name(),HistoryAction.RECHARGE.name());
        List<History> listItems_3= historyRepo.getTransactionListByDay(1L, "2022-12-01",HistoryAction.WITHDRAW.name(),HistoryAction.RECHARGE.name());


        if(listItems_2.isEmpty()==false){
            for(History item : listItems_2){
                System.out.println("Date is :" + item.getHistoryNotedDate());
            }
        }else{
            System.out.print("Fail !");
        }


    }

}
