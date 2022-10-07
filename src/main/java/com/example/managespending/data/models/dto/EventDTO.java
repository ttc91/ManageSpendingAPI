package com.example.managespending.data.models.dto;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDTO extends BaseDTO implements Serializable {

    private Long eventId;

    private String eventName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date eventEndDate;

    private Boolean eventStatus;

    private String eventIcon;

    @JsonBackReference
    private AccountDTO account;

    private WalletDTO wallet;

    private List<HistoryDTO> histories;

}
