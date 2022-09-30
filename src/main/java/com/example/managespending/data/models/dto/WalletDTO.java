package com.example.managespending.data.models.dto;


import com.example.managespending.data.models.dto.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletDTO extends BaseDTO implements Serializable {

    private Long walletId;

    private String walletName;

    private BigDecimal walletBalance;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;

    private AccountDTO account;

}
