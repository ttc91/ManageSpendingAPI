package com.example.managespending;

import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Event;
import com.example.managespending.data.models.entities.Goal;
import com.example.managespending.data.remotes.repositories.AccountRepository;
import com.example.managespending.data.remotes.repositories.EventRepository;
import com.example.managespending.data.remotes.repositories.GoalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ManagespendingApplicationTests {

    @Autowired
    private GoalRepository repo;

    @Autowired
    private AccountRepository accRepo;

    @Autowired
    private EventRepository eventRepo;



    @Test
    void contextLoads() {

        Account acc = accRepo.findAccountByAccountUsername("phuc");

        List<Goal> list = repo.findGoalsByAccountAndGoalStatus(acc, false);

        List<Event> eve = eventRepo.findEventsByAccountAndEventStatus(acc, false);

        for(Event item : eve){
            System.out.println(item.getEventName() + "  ");
        }
    }

}
