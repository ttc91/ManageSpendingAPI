package com.example.managespending.data.remotes.service.impl;

import com.example.managespending.data.mapper.WalletMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.WalletDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.utils.ResponseCode;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Wallet;
import com.example.managespending.data.remotes.repositories.AccountRepository;
import com.example.managespending.data.remotes.repositories.WalletRepository;
import com.example.managespending.data.remotes.service.WalletService;
import com.example.managespending.data.remotes.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class WalletServiceImpl extends BaseService<BaseDTO> implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WalletMapper mapper;

    @Override
    public ResponseDTO<BaseDTO> create(BaseDTO baseDTO) {

        try{

            Account account = accountRepository.findAccountByAccountUsername(((WalletDTO) baseDTO).getAccount().getAccountUsername());

            if(account == null){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Account not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }else if (((WalletDTO) baseDTO).getWalletName().equals("")) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input name of wallet")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }else if (walletRepository.findByAccountAndWalletName(account, ((WalletDTO) baseDTO).getWalletName()) != null){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Name of wallet exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Wallet wallet = mapper.mapToEntity((WalletDTO) baseDTO, Wallet.class);
            wallet.setAccount(account);
            walletRepository.save(wallet);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Insert wallet complete !!!")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(wallet, WalletDTO.class))
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Insert wallet fail !!!")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> update(BaseDTO baseDTO) {

       try{

           Account account = accountRepository.findAccountByAccountUsername(((WalletDTO) baseDTO).getAccount().getAccountUsername());
           Wallet wallet = walletRepository.findById(((WalletDTO) baseDTO).getWalletId()).get();

           if(account == null || wallet == null){

               return ResponseDTO.<BaseDTO>builder()
                       .message("Account or wallet not exist !!!")
                       .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                       .createdTime(LocalDateTime.now())
                       .build();

           }else if (((WalletDTO) baseDTO).getWalletName().equals("") || ((WalletDTO) baseDTO).getWalletBalance() == null
                || ((WalletDTO) baseDTO).getWalletName() == null) {

               return ResponseDTO.<BaseDTO>builder()
                       .message("Please input name of wallet or balance")
                       .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                       .createdTime(LocalDateTime.now())
                       .build();

           }

           wallet = mapper.mapToEntity((WalletDTO) baseDTO, Wallet.class);
           wallet.setAccount(account);

           walletRepository.save(wallet);

           return ResponseDTO.<BaseDTO>builder()
                   .message("Update wallet complete !!!")
                   .statusCode(ResponseCode.RESPONSE_OK_CODE)
                   .createdTime(LocalDateTime.now())
                   .object(mapper.mapToDTO(wallet, WalletDTO.class))
                   .build();

       }catch (Exception e){
           e.printStackTrace();
           return ResponseDTO.<BaseDTO>builder()
                   .message("Update wallet fail !!!")
                   .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                   .createdTime(LocalDateTime.now())
                   .build();
       }

    }

    @Override
    public ResponseDTO<BaseDTO> delete(BaseDTO baseDTO) {

        try{

            Wallet wallet = walletRepository.findById(((WalletDTO) baseDTO).getWalletId()).get();
            walletRepository.delete(wallet);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete wallet complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete wallet complete !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> getAll(BaseDTO baseDTO) {

        try{

            Account account = accountRepository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());
            List<Wallet> wallets = walletRepository.findAllByAccount(account);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get wallets complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .objectList(mapper.mapToDTOList(wallets, WalletDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get wallets fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> getOne(BaseDTO baseDTO) {

        try{

            Wallet wallet = walletRepository.findById(((WalletDTO) baseDTO).getWalletId()).get();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get wallet complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .object(mapper.mapToDTO(wallet, WalletDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get wallet fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

}
