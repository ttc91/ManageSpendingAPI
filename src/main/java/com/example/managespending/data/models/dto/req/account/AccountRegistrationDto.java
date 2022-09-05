package com.example.managespending.data.models.dto.req.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegistrationDto implements Serializable {
    private String username;
    private String password;
    private String rePassword;
}
