package com.example.managespending.data.models.dto.req.category.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletCategoryUpdateDto implements Serializable {

    private String oldWalletCategoryName;
    private String newWalletCategoryName;

}
