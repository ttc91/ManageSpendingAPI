package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.RAP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RAPRepository extends JpaRepository<RAP, Long> {
}
