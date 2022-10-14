package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.EventDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.remotes.service.EventService;
import com.example.managespending.utils.ApiPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiPaths.EVENT_DOMAIN)
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    EventService service;

    @PostMapping(value = ApiPaths.MODEL_CREATE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> create (@RequestBody EventDTO request) {

        try{
            return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = ApiPaths.MODEL_UPDATE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> update (@RequestBody EventDTO request) {

        try{
            return new ResponseEntity<>(service.update(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = ApiPaths.MODEL_DELETE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> delete (@RequestBody EventDTO request) {

        try{
            return new ResponseEntity<>(service.delete(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = ApiPaths.MODEL_GET_ONE_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> getOne (@RequestBody EventDTO request) {

        try{
            return new ResponseEntity<>(service.getOne(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = ApiPaths.MODEL_GET_LIST_DOMAIN)
    public ResponseEntity<ResponseDTO<BaseDTO>> getAll (@RequestBody AccountDTO request) {

        try{
            return new ResponseEntity<>(service.getAll(request), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
