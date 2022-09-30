package com.example.managespending.data.models.dto.request.rap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RAPCreateDto {

    private String rapName;
    private String rapCategoryName;
    private String rapCategoryType;
    private String rapLogo;

}
