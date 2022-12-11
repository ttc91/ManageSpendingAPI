package com.example.managespending.data.remotes.services;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;

public interface HistoryService {
    ResponseDTO<BaseDTO> create(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> delete(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> update(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getAllByWithdrawPieChart(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getAllByRechargePieChart(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getAllByWithdrawBarChart(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getListTransactionByDate(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getListDaysHaveTransaction(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getTotalCostWithdraw(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getTotalCostRecharge(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getTransactionByEvent(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getTotalCostByEvent(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getListDayHaveTransactionByEvent(BaseDTO baseDTO);

    ResponseDTO<BaseDTO> getBarItemMonth(BaseDTO baseDTO);

//    ResponseDTO<BaseDTO> getBarItemWeek(BaseDTO baseDTO);
}
