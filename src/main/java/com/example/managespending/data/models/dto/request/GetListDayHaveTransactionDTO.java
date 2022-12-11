package com.example.managespending.data.models.dto.request;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetListDayHaveTransactionDTO extends BaseDTO implements Serializable {

    private String accountUsername;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String date;

    private Long eventId;
}
