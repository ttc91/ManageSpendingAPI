package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.HistoryDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.dto.request.GetTotalBetweenDate;
import com.example.managespending.data.models.dto.request.GetTotalEventDTO;
import com.example.managespending.data.models.dto.request.GetTotalExpenseDTO;
import com.example.managespending.data.models.dto.request.GetListDayHaveTransactionDTO;
import com.example.managespending.data.remotes.service.HistoryService;
import com.example.managespending.utils.ApiPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiPaths.HISTORY_DOMAIN)
@CrossOrigin(origins = "*")
public class HistoryController {


    @Autowired
    HistoryService service;

    @PostMapping(value = ApiPaths.MODEL_CREATE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> create (@RequestBody HistoryDTO request) {

        try{
            return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = ApiPaths.MODEL_UPDATE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> update (@RequestBody HistoryDTO request) {

        try{
            return new ResponseEntity<>(service.update(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = ApiPaths.MODEL_DELETE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> delete (@RequestBody HistoryDTO request) {

        try{
            return new ResponseEntity<>(service.delete(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_BY_WITHDRAW_PIE_CHART)
    public ResponseEntity<ResponseDTO<BaseDTO>> getAllByWithdrawPieChart (@RequestBody GetTotalExpenseDTO request) {

        try{
            return new ResponseEntity<>(service.getAllByWithdrawPieChart(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_BY_RECHARGE_PIE_CHART)
    public ResponseEntity<ResponseDTO<BaseDTO>> getAllByRechargePieChart (@RequestBody GetTotalExpenseDTO request) {

        try{
            return new ResponseEntity<>(service.getAllByRechargePieChart(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_BY_WITHDRAW_BAR_CHART)
    public ResponseEntity<ResponseDTO<BaseDTO>> getAllByWithdrawBarChar (@RequestBody AccountDTO request) {
        try{
            return new ResponseEntity<>(service.getAllByWithdrawBarChart(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_DAY_IN_MONTH)
    public ResponseEntity<ResponseDTO<BaseDTO>> getListDayInMonth(@RequestBody GetListDayHaveTransactionDTO request){
        try{
            return new ResponseEntity<>(service.getListDaysHaveTransaction(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = ApiPaths.HISTORY_GET_TOTAL_COST_OF_WITHDRAW)
    public ResponseEntity<ResponseDTO<BaseDTO>> getTotalCostOfWithdraw(@RequestBody GetTotalExpenseDTO request){
        try{
            return new ResponseEntity<>(service.getTotalCostWithdraw(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ApiPaths.HISTORY_GET_TOTAL_COST_OF_RECHARGE)
    public ResponseEntity<ResponseDTO<BaseDTO>> getTotalCostOfRecharge(@RequestBody GetTotalExpenseDTO request){
        try{
            return new ResponseEntity<>(service.getTotalCostRecharge(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_BY_DATE)
    public ResponseEntity<ResponseDTO<BaseDTO>> getListTransactionByDate(@RequestBody GetTotalExpenseDTO request){
        try{
            return new ResponseEntity<>(service.getListTransactionByDate(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_DAY_BY_EVENT)
    public ResponseEntity<ResponseDTO<BaseDTO>> getListDayByEvent(@RequestBody GetListDayHaveTransactionDTO request){
        try{
            return new ResponseEntity<>(service.getListDayHaveTransactionByEvent(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_BY_EVENT)
    public ResponseEntity<ResponseDTO<BaseDTO>> getListTransactionByEvent(@RequestBody GetTotalEventDTO request){
        try{
            return new ResponseEntity<>(service.getTransactionByEvent(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = ApiPaths.HISTORY_GET_TOTAL_COST_BY_EVENT)
    public ResponseEntity<ResponseDTO<BaseDTO>> getTotalCostByEvent(@RequestBody GetTotalEventDTO request){
        try{
            return new ResponseEntity<>(service.getTotalCostByEvent(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ApiPaths.HISTORY_GET_TOTAL_COST_BETWEEN_DATE)
    public ResponseEntity<ResponseDTO<BaseDTO>> getTotalCostBetweenDate(@RequestBody GetTotalBetweenDate request){
        try{
            return new ResponseEntity<>(service.getBarItemMonth(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
