package com.example.managespending.controllers;


import com.example.managespending.data.models.dto.request.rap.RAPCreateDto;
import com.example.managespending.data.models.dto.request.rap.RAPDeleteDto;
import com.example.managespending.data.models.dto.request.rap.RAPUpdateDto;
import com.example.managespending.data.models.entities.RAP;
import com.example.managespending.data.models.entities.RAPCategory;
import com.example.managespending.data.remotes.service.RAPCategoryService;
import com.example.managespending.data.remotes.service.RAPService;
import com.example.managespending.data.remotes.service.WalletService;
import com.example.managespending.utils.PathApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathApi.RECEIPTS_AND_PAYMENTS_DOMAIN)
public class RAPController {

//    @Autowired
//    private RAPService rapService;
//
//    @Autowired
//    private WalletService walletService;
//
//    @Autowired
//    private RAPCategoryService rapCategoryService;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @PostMapping(PathApi.MODEL_INSERT_DOMAIN)
//    public ResponseEntity<RAP> insert(@RequestBody RAPCreateDto request) {
//        RAPCategory rapCate = rapCategoryService.findRAPCategoryByRapCategoryName(request.getRapCategoryName());
//        RAP rap = rapService.findRAPByRapName(request.getRapName());
//        if (rapCate != null && rap == null) {
//            RAP object = modelMapper.map(request, RAP.class);
//            rapService.save(object);
//            return new ResponseEntity<>(object, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @PutMapping(PathApi.MODEL_UPDATE_DOMAIN)
//    public ResponseEntity<RAP> update(@RequestBody RAPUpdateDto request) {
//        RAP rap = rapService.findRAPByRapName(request.getOlfRapName());
//        if (rap != null) {
//            rap.setRapName(request.getNewRapName());
//            rap.setRapCategory(rapCategoryService.findRAPCategoryByRapCategoryName(request.getNewRapCategoryName()));
//            rap.setRapLogo(request.getNewRapLogo());
//            rap.getRapCategory().setRapCategoryType(request.getNewRapCategoryType());
//            rapService.save(rap);
//            return new ResponseEntity<>(rap, HttpStatus.UPGRADE_REQUIRED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @DeleteMapping(PathApi.MODEL_UPDATE_DOMAIN)
//    public ResponseEntity<RAP> delete(@RequestBody RAPDeleteDto request) {
//        RAP rap = rapService.findRAPByRapName(request.getRapName());
//        if (rap != null) {
//            rapService.delete(rap);
//            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @GetMapping(PathApi.MODEL_GETBYNAME_DOMAIN + "/{rapName}")
//    public ResponseEntity<RAP> getRAPByName(@PathVariable("rapName") String name) {
//        RAP rap = rapService.findRAPByRapName(name);
//        if (rap != null)
//            return new ResponseEntity<>(rap, HttpStatus.FOUND);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping(PathApi.MODEL_GETLIST_DOMAIN)
//    public ResponseEntity<List<RAP>> getAllRAP() {
//        List<RAP> list = rapService.findAll();
//        if (list.isEmpty() == false)
//            return new ResponseEntity<>(list, HttpStatus.FOUND);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping(PathApi.MODEL_GETBYID_DOMAIN + "/{rapID}")
//    public ResponseEntity<RAP> getRAPByID(@PathVariable("rapID") long id) {
//        RAP rap = rapService.findById(id).orElse(null);
//        if (rap != null)
//            return new ResponseEntity<>(rap, HttpStatus.FOUND);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}
