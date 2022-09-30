package com.example.managespending.data.remotes.repositories;

import com.example.managespending.data.models.entities.RAPCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RAPCategoryRepository extends JpaRepository<RAPCategory, Long> {

    //public RAPCategory findRAPCategoryByRapCategoryName(String rapCategoryName);

}
