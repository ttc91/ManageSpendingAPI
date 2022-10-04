package com.example.managespending.data.models.dto;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoalDTO extends BaseDTO implements Serializable {

    private Long goalId;

    private String goalName;

    private Boolean goalStatus;

    private BigDecimal goalPresentCost;

    private BigDecimal goalFinalCost;

    private BigDecimal goalDepositCost;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date goalStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date goalEndDate;

    private String goalIcon;

    private String goalColor;

    @JsonBackReference
    private AccountDTO account;

    private Long walletId;

    private List<HistoryDTO> histories;

}
