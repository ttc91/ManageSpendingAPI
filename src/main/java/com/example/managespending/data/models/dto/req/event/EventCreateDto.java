package com.example.managespending.data.models.dto.req.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventCreateDto {
    private String eventName;
    private Date endDate;
    private Date startDate;
}
