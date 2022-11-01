package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;

public interface HistoryService {
    ResponseDTO<BaseDTO> create(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> delete(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> update(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getAllByWithdraw(BaseDTO baseDTO);
}
