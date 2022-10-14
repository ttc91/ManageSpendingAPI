package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.HistoryDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
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

}
