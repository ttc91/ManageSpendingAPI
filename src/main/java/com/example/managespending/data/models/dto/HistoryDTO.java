package com.example.managespending.data.models.dto;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.utils.enums.HistoryAction;
import com.example.managespending.utils.enums.HistoryType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryDTO extends BaseDTO implements Serializable {

    private Long historyId;

    private HistoryType historyType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date historyNotedDate;

    private HistoryAction historyAction;

    private BigDecimal historyCost;

    private String historyNote;

    private String accountUsername;

    private Long walletId;

    private Long eventId;

    private Long expenseId;

}
