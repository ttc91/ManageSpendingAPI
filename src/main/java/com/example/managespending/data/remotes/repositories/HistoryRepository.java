package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Goal;
import com.example.managespending.data.models.entities.History;
import com.example.managespending.utils.enums.HistoryAction;
import com.example.managespending.utils.enums.HistoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
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

}