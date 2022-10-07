package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Budget;
import com.example.managespending.data.models.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Budget findBudgetByAccountAndBudgetName(Account account, String budgetName);
    List<Budget> findAllByAccount(Account account);
    Budget findBudgetByAccountAndExpense(Account account, Expense expense);

}
