package com.example.managespending.data.remotes.service.impl;

import com.example.managespending.data.mapper.AccountMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.utils.ResponseCode;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.remotes.repositories.AccountRepository;
import com.example.managespending.data.remotes.service.AccountService;
import com.example.managespending.data.remotes.service.base.BaseService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AccountServiceImpl extends BaseService<BaseDTO> implements AccountService {

    @Autowired
    AccountRepository repository;

    @Autowired
    AccountMapper mapper;

    @Override
    public ResponseDTO<BaseDTO> create(BaseDTO baseDTO) {

        try {

            if(((AccountDTO) baseDTO).getRePassword() == null || ((AccountDTO) baseDTO).getRePassword().equals("")){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input correct password !")
                        .createdTime(LocalDateTime.now())
                        .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                        .build();
            }

            if(!((AccountDTO) baseDTO).getAccountPassword().equals(((AccountDTO) baseDTO).getRePassword())){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Password is not same !")
                        .createdTime(LocalDateTime.now())
                        .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                        .build();
            }

            ((AccountDTO) baseDTO).setAccountPassword(BCrypt.hashpw(((AccountDTO) baseDTO).getAccountPassword(), BCrypt.gensalt(12)));
            Account account = repository.save(mapper.mapToEntity(((AccountDTO) baseDTO), Account.class));

            return ResponseDTO.<BaseDTO>builder()
                    .message("Create account complete !")
                    .object(mapper.mapToDTO(account, AccountDTO.class))
                    .createdTime(LocalDateTime.now())
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .build();

        }catch (Exception e){
            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Create account fail !")
                    .createdTime(LocalDateTime.now())
                    .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> signIn(BaseDTO baseDTO) {

        try{

            Account account = repository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountPassword());

            if(((AccountDTO) baseDTO).getAccountPassword().equals("") || ((AccountDTO) baseDTO).getAccountUsername().equals("")){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input username or password !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            if(BCrypt.checkpw(((AccountDTO) baseDTO).getAccountPassword(), account.getAccountPassword())){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Sign in complete !!!")
                        .statusCode(ResponseCode.RESPONSE_OK_CODE)
                        .createdTime(LocalDateTime.now())
                        .object(mapper.mapToDTO(account, AccountDTO.class))
                        .build();
            }

            return ResponseDTO.<BaseDTO>builder()
                    .message("Sign in fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Sign in fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public ResponseDTO<BaseDTO> changePassword(BaseDTO baseDTO) {

        try{

            Account account = repository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());

            if(((AccountDTO) baseDTO).getNewPassword().equals("") || ((AccountDTO) baseDTO).getNewPassword() == null ||
                ((AccountDTO) baseDTO).getAccountPassword().equals("") || ((AccountDTO) baseDTO).getAccountPassword() == null ||
                    ((AccountDTO) baseDTO).getRePassword().equals("") || ((AccountDTO) baseDTO).getRePassword() == null){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input fill field before update password !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }else if (! BCrypt.checkpw(((AccountDTO) baseDTO).getAccountPassword(), account.getAccountPassword())){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Present password is not correct !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }else if ( BCrypt.checkpw(((AccountDTO) baseDTO).getNewPassword(), account.getAccountPassword()) ){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Your new password and present password is same please input again !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }else if ( !((AccountDTO) baseDTO).getNewPassword().equals(((AccountDTO) baseDTO).getRePassword())){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input same value of two field in new password !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            account.setAccountPassword(BCrypt.hashpw(((AccountDTO) baseDTO).getNewPassword(), BCrypt.gensalt(12)));
            repository.save(account);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Change password complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(account, AccountDTO.class))
                    .build();

        }catch (Exception e){

            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Change password fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

}
