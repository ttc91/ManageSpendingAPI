package com.example.managespending.controllers;


import com.example.managespending.data.models.dto.request.event.EventCreateDto;
import com.example.managespending.data.models.dto.request.event.EventDeleteDto;
import com.example.managespending.data.models.dto.request.event.EventUpdateDto;
import com.example.managespending.data.models.entities.Event;
import com.example.managespending.data.remotes.service.EventService;
import com.example.managespending.data.remotes.service.TransactionService;
import com.example.managespending.data.remotes.service.WalletService;
import com.example.managespending.utils.PathApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathApi.EVENT_DOMAIN)
public class EventController {


    @Autowired
    private EventService eventService;

//    @Autowired
//    private TransactionService transactionService;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private WalletService walletService;

//    @PostMapping(PathApi.MODEL_INSERT_DOMAIN)
//    public ResponseEntity<Event> insert(@RequestBody EventCreateDto request) {
//
//        if (eventService.findEventByEventName(request.getEventName()) == null) {
//            Event event = modelMapper.map(request, Event.class);
//            eventService.save(event);
//            return new ResponseEntity<>(event, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping(PathApi.MODEL_UPDATE_DOMAIN)
//    public ResponseEntity<Event> update(@RequestBody EventUpdateDto request) {
//        Event event = eventService.findEventByEventName(request.getOdlEventName());
//        if (event != null) {
//            event.setEventStatus(request.getNewStatus());
//            event.setEventName(request.getNewEventName());
//            event.setEndDate(request.getNewEndDate());
//            event.setStartDate(request.getNewStartDate());
//            eventService.save(event);
//            return new ResponseEntity<>(event, HttpStatus.UPGRADE_REQUIRED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping(PathApi.MODEL_DELETE_DOMAIN)
//    public ResponseEntity<Event> delete(@RequestBody EventDeleteDto request) {
//        Event event = eventService.findEventByEventName(request.getEventName());
//        if (event != null) {
//            eventService.delete(event);
//            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @GetMapping(PathApi.MODEL_GETBYNAME_DOMAIN + "/{eventName}")
//    public ResponseEntity<Event> getEventByName(@PathVariable("eventName") String name) {
//        Event event = eventService.findEventByEventName(name);
//        if (event != null)
//            return new ResponseEntity<>(event, HttpStatus.FOUND);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping(PathApi.MODEL_GETLIST_DOMAIN)
//    public ResponseEntity<List<Event>> getALlEvent() {
//        List<Event> list = eventService.findAll();
//        if (list.isEmpty() == false)
//            return new ResponseEntity<>(list, HttpStatus.FOUND);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping(PathApi.MODEL_GETBYID_DOMAIN + "/{eventID}")
//    public ResponseEntity<Event> getEventByID(@PathVariable("eventID") long id) {
//        Event event = eventService.findById(id).orElse(null);
//        if (event != null)
//            return new ResponseEntity<>(event, HttpStatus.FOUND);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}
