package com.example.managespending.data.models.dto.req.rap;

import com.example.managespending.data.models.entities.Wallet;
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
