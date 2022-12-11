package com.example.managespending;

import com.example.managespending.data.models.entities.*;
import com.example.managespending.data.remotes.repositories.*;
import com.example.managespending.utils.enums.GetDateType;
import com.example.managespending.utils.enums.HistoryAction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Tuple;
import java.math.BigDecimal;
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

        List<Budget> budget = budgetRepo.findBudgetsByAccountAndBudgetExpired(acc, false);

        List<Expense> listExpense = expenseRepo.findAllByAccount(acc);
        List<Expense> listExpense2 = expenseRepo.findAllByIsExpenseSystem(false);

        listExpense.addAll(listExpense2);

        Expense ex = expenseRepo.findById(1L).get();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df_2 = new SimpleDateFormat("yyyy-MM");

        Date startDate = df.parse("2022-11-29");
        Date endDate = df.parse("2022-12-02");


        Date monthFormat = df_2.parse("2022-11");

        List<Tuple> listItems_4 = historyRepo.getListDaysHaveTransactionsInMonth(1L, "2022-12", HistoryAction.WITHDRAW.name(), HistoryAction.RECHARGE.name());
        List<Tuple> listItems_5 = historyRepo.getTransactionListByAccountAndHistoryAction(1L, HistoryAction.RECHARGE.name(), "2022-12-03", "MONTH");
        Tuple item_6 = historyRepo.getTotalCost(1L, "2022-12-03", HistoryAction.RECHARGE.name(), "DAY");

        List<History> listItems_7 = historyRepo.getListTransactionByDate(1L,"2022-12-02",HistoryAction.RECHARGE.name(),HistoryAction.WITHDRAW.name(), "MONTH");


        Tuple item_7  = historyRepo.getTotalCostByEvent(1L,2L,HistoryAction.WITHDRAW.name());


        Event event = eventRepo.findById(2L).get();
//        List<History> listTranByEvent = historyRepo.getTransactionByEvent(1L,2L,"2022-12-04" , GetDateType.DAY.value, HistoryAction.RECHARGE.name(), HistoryAction.WITHDRAW.name());
        List<Tuple> listDayHaveTranByEvent = historyRepo.getListDayHaveTransactionByEvent(1L,1L,"2022",HistoryAction.RECHARGE.name(), HistoryAction.WITHDRAW.name());

        Tuple totalCostBetween = historyRepo.getTotalCostBetweenDate(1L,"2022-12-08","2022-12-09","RECHARGE");

        if(item_7 == null){
            System.out.println("No item");
        }else{
//            for(Tuple item:listDayHaveTranByEvent){
//                System.out.print(item.get(0));
//            }
            System.out.println(item_7.get("total_cost",BigDecimal.class));

        }



    }

}
