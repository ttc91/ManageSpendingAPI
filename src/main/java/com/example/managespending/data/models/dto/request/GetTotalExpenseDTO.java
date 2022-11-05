package com.example.managespending.data.models.dto.request;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.utils.enums.GetDateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetTotalExpenseDTO extends BaseDTO implements Serializable {

    private String accountUsername;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String date;

    private GetDateType getDateType;

}
