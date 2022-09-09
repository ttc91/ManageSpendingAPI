package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public Event findEventByEventName(String eventName);
}
