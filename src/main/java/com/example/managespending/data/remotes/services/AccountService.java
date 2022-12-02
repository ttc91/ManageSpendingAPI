package com.example.managespending.data.remotes.services;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;

public interface AccountService {


    ResponseDTO<BaseDTO> create(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> signIn(BaseDTO baseDTO) throws Exception;

    ResponseDTO<BaseDTO> changePassword(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> updateRole(BaseDTO baseDTO);
}
