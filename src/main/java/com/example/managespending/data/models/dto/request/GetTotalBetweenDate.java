package com.example.managespending.data.models.dto.request;


import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.utils.enums.GetDateType;
import com.example.managespending.utils.enums.HistoryAction;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetTotalBetweenDate extends BaseDTO implements Serializable {

    private String accountUsername;
    private String year;
    private String month;
    private String lastDayOfMonth;
    private HistoryAction historyAction;
}
