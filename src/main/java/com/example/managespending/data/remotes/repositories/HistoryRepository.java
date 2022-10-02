package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
