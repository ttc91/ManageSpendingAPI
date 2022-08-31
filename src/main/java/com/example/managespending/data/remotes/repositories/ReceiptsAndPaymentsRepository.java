package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.ReceiptsAndPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptsAndPaymentsRepository extends JpaRepository<ReceiptsAndPayments, Long> {
}
