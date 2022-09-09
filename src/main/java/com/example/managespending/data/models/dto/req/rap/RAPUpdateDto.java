package com.example.managespending.data.models.dto.req.rap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RAPUpdateDto {
    private String olfRapName;


    private String newRapName;
    private String newRapCategoryType;
    private String newRapLogo;
    private String newRapCategoryName;
}
