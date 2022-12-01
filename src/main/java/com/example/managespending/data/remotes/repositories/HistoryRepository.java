package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Expense;
import com.example.managespending.data.models.entities.Goal;
import com.example.managespending.data.models.entities.History;
import com.example.managespending.utils.enums.HistoryAction;
import com.example.managespending.utils.enums.HistoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByAccountAndGoalAndHistoryType(Account account, Goal goal, HistoryType historyType);
    List<History> findAllByAccountAndHistoryAction(Account account, HistoryAction historyAction);

    @Query(value = "SELECT expense.expense_name AS expense_name, SUM(history.history_cost) AS total_cost " +
            " FROM history " +
            " INNER JOIN expense ON expense.expense_id = history.expense_id " +
            " WHERE history.account_id = :accountId AND history.history_action = CAST( :historyAction AS CHARACTER VARYING ) AND date_trunc( :getDatesType, history.history_noted_date) = date_trunc( :getDatesType, ( :startDate ) \\:\\: timestamp ) " +
            " GROUP BY expense.expense_name", nativeQuery = true)
    List<Tuple> getTransactionListByAccountAndHistoryAction(@Param("accountId") Long accountId, @Param("historyAction") String historyAction, @Param("startDate") String startDate, @Param("getDatesType") String getDatesType);


    @Query(value = "SELECT history.*" +
            "FROM history INNER JOIN expense ON expense.expense_id = history.expense_id " +
            "WHERE history.account_id = :accountId AND history.history_noted_date <= :endDate AND history.history_noted_date >= :startDate " +
            "AND history.history_action = :historyAction1 OR " +
            "history.history_noted_date <= :endDate AND history.history_noted_date >= :startDate AND history.history_action= :historyAction2 " +
            "ORDER BY history.*", nativeQuery = true)
    List<History> getTransactionListByWeek(@Param("accountId") Long accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("historyAction1") String historyAction1, @Param("historyAction2") String historyAction2);



    @Query(value = "SELECT history.* " +
            "FROM history INNER JOIN expense ON history.expense_id = expense.expense_id " +
            "WHERE history.account_id = :accountId AND " +
//            "to_char(history.history_noted_date,'yyyy-MM') LIKE CONCAT(:month,'%') "+
            "to_char(history.history_noted_date,'yyyy-MM') LIKE %:month% AND history.history_action = :historyAction1 OR " +
            "to_char(history.history_noted_date,'yyyy-MM') LIKE %:month% AND history.history_action= :historyAction2 " +
            "ORDER BY history.* ",nativeQuery = true)
    List<History> getTransactionListByMonth(@Param("accountId") Long accountId, @Param("month") String month, @Param("historyAction1") String historyAction1, @Param("historyAction2") String historyAction2);


    @Query(value = "SELECT history.* " +
            "FROM history INNER JOIN expense ON history.expense_id = expense.expense_id " +
            "WHERE history.account_id = :accountId AND " +
            "to_char(history.history_noted_date,'yyyy-MM-dd') = :day AND history.history_action = :historyAction1 OR " +
            "to_char(history.history_noted_date,'yyyy-MM-dd') = :day AND history.history_action= :historyAction2 " +
            "ORDER BY history.* ",nativeQuery = true)
    List<History> getTransactionListByDay(@Param("accountId") Long accountId, @Param("day") String day, @Param("historyAction1") String historyAction1, @Param("historyAction2") String historyAction2);



    @Query(value = "SELECT history.history_noted_date " +
            "FROM history INNER JOIN expense ON history.expense_id = expense.expense_id " +
            "WHERE history.account_id = :accountId AND " +
            "to_char(history.history_noted_date,'yyyy-MM') LIKE %:month% AND history.history_action = :historyAction1 OR " +
            "to_char(history.history_noted_date,'yyyy-MM') LIKE %:month% AND history.history_action= :historyAction2 " +
            "GROUP BY history.history_noted_date ",nativeQuery = true)
    List<Tuple> getListDaysHaveTransactionsInMonth(@Param("accountId") Long accountId, @Param("month") String month, @Param("historyAction1") String historyAction1, @Param("historyAction2") String historyAction2);

}