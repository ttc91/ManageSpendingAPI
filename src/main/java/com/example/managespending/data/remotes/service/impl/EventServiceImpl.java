package com.example.managespending.data.remotes.service.impl;

import com.example.managespending.data.mapper.EventMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.EventDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Event;
import com.example.managespending.data.models.entities.History;
import com.example.managespending.data.remotes.repositories.AccountRepository;
import com.example.managespending.data.remotes.repositories.EventRepository;
import com.example.managespending.data.remotes.repositories.HistoryRepository;
import com.example.managespending.data.remotes.repositories.WalletRepository;
import com.example.managespending.data.remotes.service.EventService;
import com.example.managespending.data.remotes.service.base.BaseService;
import com.example.managespending.utils.ResponseCode;
import com.example.managespending.utils.enums.HistoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl extends BaseService<BaseDTO> implements EventService {

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventMapper mapper;

    @Override
    public ResponseDTO<BaseDTO> create(BaseDTO baseDTO) {

        try{

            if(((EventDTO) baseDTO).getEventName().length() == 0 ||
                    ((EventDTO) baseDTO).getEventName() == null ||
                    ((EventDTO) baseDTO).getEventIcon().length() == 0 ||
                    ((EventDTO) baseDTO).getEventIcon() == null ||
                    ((EventDTO) baseDTO).getEventEndDate() == null ||
                    ((EventDTO) baseDTO).getAccount() == null
            ){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input name or icon or event end date or account for event entity !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            if(((EventDTO) baseDTO).getEventEndDate().compareTo(new Date()) < 0) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("The end date must be after or equal than now !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Event event = mapper.mapToEntity((EventDTO) baseDTO, Event.class);

            event.setEventStatus(false);
            event.setAccount(accountRepository.findAccountByAccountUsername(((EventDTO) baseDTO).getAccount().getAccountUsername()));

            if(((EventDTO) baseDTO).getWallet() != null){

                if(! walletRepository.findById(((EventDTO) baseDTO).getWallet().getWalletId()).isPresent()){

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Wallet is not exist !")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();

                }

                event.setWallet( walletRepository.findById(((EventDTO) baseDTO).getWallet().getWalletId()).get());

            }

            eventRepository.save(event);

            History history = new History();
            history.setAccount(accountRepository.findAccountByAccountUsername(((EventDTO) baseDTO).getAccount().getAccountUsername()));
            history.setHistoryType(HistoryType.CREATE);
            history.setHistoryNote("Created new event name " + event.getEventName());
            history.setEvent(event);

            historyRepository.save(history);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Create event complete !!!")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(event, EventDTO.class))
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Create event fail !")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> update(BaseDTO baseDTO) {

        try{

            if(((EventDTO) baseDTO).getEventName().length() == 0 ||
                    ((EventDTO) baseDTO).getEventName() == null ||
                    ((EventDTO) baseDTO).getEventIcon().length() == 0 ||
                    ((EventDTO) baseDTO).getEventIcon() == null ||
                    ((EventDTO) baseDTO).getEventEndDate() == null
            ){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input name or icon or event end date or account for event entity !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            if(((EventDTO) baseDTO).getEventEndDate().compareTo(new Date()) < 0) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("The end date must be after or equal than now !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Optional<Event> eventOpt = eventRepository.findById(((EventDTO) baseDTO).getEventId());

            if(!eventOpt.isPresent()){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Event is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Event event = eventOpt.get();
            event.setEventStatus(((EventDTO) baseDTO).getEventStatus());
            event.setEventIcon(((EventDTO) baseDTO).getEventIcon());
            event.setEventName(((EventDTO) baseDTO).getEventName());
            event.setEventEndDate(((EventDTO) baseDTO).getEventEndDate());

            if(((EventDTO) baseDTO).getWallet() != null){

                if(! walletRepository.findById(((EventDTO) baseDTO).getWallet().getWalletId()).isPresent()){

                    return ResponseDTO.<BaseDTO>builder()
                            .message("Wallet is not exist !")
                            .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                            .createdTime(LocalDateTime.now())
                            .build();

                }

                event.setWallet( walletRepository.findById(((EventDTO) baseDTO).getWallet().getWalletId()).get());

            } else {

                event.setWallet(null);

            }

            eventRepository.save(event);

            History history = new History();
            history.setAccount(accountRepository.findAccountByAccountUsername(event.getAccount().getAccountUsername()));
            history.setHistoryType(HistoryType.UPDATE);
            history.setHistoryNote("Updated new event name " + event.getEventName());
            history.setEvent(event);

            historyRepository.save(history);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Update event complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(event, EventDTO.class))
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Update event fail !")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> delete(BaseDTO baseDTO) {

        try{

            History history = new History();
            history.setAccount(accountRepository.findAccountByAccountUsername(((EventDTO) baseDTO).getAccount().getAccountUsername()));
            history.setHistoryType(HistoryType.REMOVE);
            history.setHistoryNote("Deleted event name " + ((EventDTO) baseDTO).getEventName());

            Optional<Event> opt = eventRepository.findById(((EventDTO) baseDTO).getEventId());

            if(!opt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Event object is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Event event = opt.get();

            eventRepository.delete(event);
            historyRepository.save(history);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete event complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete event fail !")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> getOne(BaseDTO baseDTO) {

        try{

            Optional<Event> opt = eventRepository.findById(((EventDTO) baseDTO).getEventId());

            if(!opt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Event object is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get event complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(opt.get(), EventDTO.class))
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get event fail !")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> getAll(BaseDTO baseDTO) {

        try{

            Account account = accountRepository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());
            List<Event> events = eventRepository.findAllByAccount(account);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get events complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .objectList(mapper.mapToDTOList(events, EventDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get events fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }
}
