package com.example.managespending.data.remotes.services;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;

public interface RoleService {
    ResponseDTO<BaseDTO> create(BaseDTO baseDTO);
}
