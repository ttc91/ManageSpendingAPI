package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.HistoryDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.dto.request.GetTotalExpenseDTO;
import com.example.managespending.data.models.dto.request.GetTransactionListByDayDTO;
import com.example.managespending.data.models.dto.request.GetTransactionListByMonthDTO;
import com.example.managespending.data.models.dto.request.GetTransactionListByWeekDTO;
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


    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_BY_WEEK)
    public ResponseEntity<ResponseDTO<BaseDTO>> getListByWeek(@RequestBody GetTransactionListByWeekDTO request){
        try{
            return new ResponseEntity<>(service.getTransactionByWeek(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_BY_MONTH)
    public ResponseEntity<ResponseDTO<BaseDTO>> getListByMonth(@RequestBody GetTransactionListByMonthDTO request){
        try{
            return new ResponseEntity<>(service.getTransactionByMonth(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ApiPaths.HISTORY_GET_LIST_BY_DAY)
    public ResponseEntity<ResponseDTO<BaseDTO>> getListByDay(@RequestBody GetTransactionListByDayDTO request){
        try{
            return new ResponseEntity<>(service.getTransactionByDay(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
