package com.example.managespending.data.models.dto;

import com.example.managespending.data.models.dto.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO extends BaseDTO implements Serializable {

    private Long id;

    @NotNull(message = "Please input your username !")
    private String username;

    private String password;

    private String newPassword;

    private String rePassword;

    private Date createdTime;

    private Date updatedTime;

}
