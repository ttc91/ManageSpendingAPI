package com.example.managespending.data.models.dto;

import com.example.managespending.data.models.dto.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO extends BaseDTO implements Serializable {

    private Long accountId;

    @NotNull(message = "Please input your username !")
    private String accountUsername;

    private String accountPassword;

    private String newPassword;

    private String rePassword;

}
