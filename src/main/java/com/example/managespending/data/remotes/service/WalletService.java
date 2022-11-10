package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.entities.Wallet;

import java.util.List;

public interface WalletService {

    ResponseDTO<BaseDTO> create(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> update(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> delete(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getAll(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getOne(BaseDTO baseDTO);
}
