package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;

public interface HistoryService {
    ResponseDTO<BaseDTO> create(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> delete(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> update(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getAllByWithdrawPieChart(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getAllByRechargePieChart(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getAllByWithdrawBarChart(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getTransactionByWeek(BaseDTO baseDTO);
    ResponseDTO<BaseDTO> getTransactionByMonth(BaseDTO baseDTO);
    ResponseDTO<BaseDTO> getTransactionByDay(BaseDTO baseDTO);
    ResponseDTO<BaseDTO> getListDaysHaveTransaction(BaseDTO baseDTO);
}
