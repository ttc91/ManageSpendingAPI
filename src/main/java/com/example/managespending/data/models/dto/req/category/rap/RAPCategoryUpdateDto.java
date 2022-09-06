package com.example.managespending.data.models.dto.req.category.rap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RAPCategoryUpdateDto {

    private String oldRAPCategoryName;
    private String newRAPCategoryName;

}
