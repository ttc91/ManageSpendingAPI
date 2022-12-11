package com.example.managespending.data.models.dto.request;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.utils.enums.GetDateType;
import com.example.managespending.utils.enums.HistoryAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetTotalEventDTO extends BaseDTO implements Serializable {

    private Long eventId;

    private String accountUsername;

    private HistoryAction historyAction;

    private String date;

    private GetDateType getDateType;
}
