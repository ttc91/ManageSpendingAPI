package com.example.managespending.data.models.dto.response;

import com.example.managespending.data.models.dto.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO extends BaseDTO implements Serializable {

    private String token;

}
