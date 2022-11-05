package com.example.managespending.data.models.dto.response;

import com.example.managespending.data.models.dto.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PieItemDTO extends BaseDTO implements Serializable {

    private String expenseName;
    private BigDecimal totalCost;

}
