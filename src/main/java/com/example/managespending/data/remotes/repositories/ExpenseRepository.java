package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Expense;
import com.example.managespending.data.models.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByAccount(Account account);
    Expense findByExpenseName(String expenseName);
    Expense findByExpenseNameAndAccount(String expenseName, Account account);
    Expense findByExpenseNameAndIsExpenseSystem(String expenseName, Boolean isExpenseSystem);
    List<Expense> findAllByIsExpenseSystem(Boolean isExpenseSystem);

}
