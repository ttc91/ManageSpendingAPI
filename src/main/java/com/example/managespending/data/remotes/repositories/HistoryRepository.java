package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Goal;
import com.example.managespending.data.models.entities.History;
import com.example.managespending.utils.enums.HistoryAction;
import com.example.managespending.utils.enums.HistoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByAccountAndGoalAndHistoryType(Account account, Goal goal, HistoryType historyType);
    List<History> findAllByAccountAndHistoryAction(Account account, HistoryAction historyAction);

}
