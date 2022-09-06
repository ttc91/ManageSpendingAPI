package com.example.managespending.controllers;

import com.example.managespending.data.models.dto.req.category.rap.RAPCategoryDto;
import com.example.managespending.data.models.dto.req.category.rap.RAPCategoryUpdateDto;
import com.example.managespending.data.models.entities.RAPCategory;
import com.example.managespending.data.models.entities.WalletCategory;
import com.example.managespending.data.remotes.service.RAPCategoryService;
import com.example.managespending.utils.PathApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(PathApi.RECEIPTS_AND_PAYMENTS_CATEGORY_DOMAIN)
public class RAPCategoryController {

    @Autowired
    private RAPCategoryService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(PathApi.MODEL_INSERT_DOMAIN)
    public ResponseEntity<RAPCategory> insert(@RequestBody RAPCategoryDto request){

        try{
            RAPCategory rapCategory = modelMapper.map(request, RAPCategory.class);
            service.save(rapCategory);
            return new ResponseEntity<>(rapCategory, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(PathApi.MODEL_UPDATE_DOMAIN)
    public ResponseEntity<WalletCategory> update(@RequestBody RAPCategoryUpdateDto request){

        RAPCategory rapCategory = service.findRAPCategoryByRapCategoryName(request.getOldRAPCategoryName());
        if(rapCategory != null){
            rapCategory.setRapCategoryName(request.getNewRAPCategoryName());
            service.save(rapCategory);
            return new ResponseEntity(rapCategory, HttpStatus.UPGRADE_REQUIRED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(PathApi.MODEL_DELETE_DOMAIN)
    public ResponseEntity<WalletCategory> delete(@RequestBody RAPCategoryDto request){

        RAPCategory rapCategory = service.findRAPCategoryByRapCategoryName(request.getRapCategoryName());
        if(rapCategory != null){
            service.delete(rapCategory);
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
