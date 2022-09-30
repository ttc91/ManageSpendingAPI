package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Budget;
import com.example.managespending.data.models.entities.RAP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    //public Budget findBudgetByRap (RAP rap);
}
