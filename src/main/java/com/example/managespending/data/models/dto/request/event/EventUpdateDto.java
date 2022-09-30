package com.example.managespending.data.models.dto.request.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventUpdateDto {
    private String odlEventName;

    private int newStatus;
    private String newEventName;
    private Date newEndDate;
    private Date newStartDate;
    private long newAccountId;
    private String newWalletName;


}
