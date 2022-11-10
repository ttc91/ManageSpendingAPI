package com.example.managespending.data.remotes.service.impl;

import com.example.managespending.data.mapper.AccountMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.dto.request.JwtRequestDTO;
import com.example.managespending.data.models.dto.response.JwtResponseDTO;
import com.example.managespending.data.models.entities.Role;
import com.example.managespending.data.remotes.repositories.RoleRepository;
import com.example.managespending.utils.ResponseCode;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.remotes.repositories.AccountRepository;
import com.example.managespending.data.remotes.service.AccountService;
import com.example.managespending.data.remotes.service.base.BaseService;
import com.example.managespending.utils.config.security.MyAuthenticationManager;
import com.example.managespending.utils.config.security.jwt.JwtTokenProvider;
import com.example.managespending.utils.config.security.user.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class AccountServiceImpl extends BaseService<BaseDTO> implements AccountService {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    MyAuthenticationManager myAuthenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

            if(((AccountDTO) baseDTO).getRole() == null){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input role !")
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

            ((AccountDTO) baseDTO).setAccountPassword(passwordEncoder.encode(((AccountDTO) baseDTO).getAccountPassword()));
            accountRepository.save(mapper.mapToEntity(((AccountDTO) baseDTO), Account.class));

            final UserDetails userDetails = myUserDetailsService.loadUserByUsername(((AccountDTO) baseDTO).getAccountUsername());
            final String jwtToken = jwtTokenProvider.generateToken(userDetails);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Sign up complete !!!")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .object(new JwtResponseDTO(jwtToken))
                    .createdTime(LocalDateTime.now())
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
    public ResponseDTO<BaseDTO> signIn(BaseDTO baseDTO) throws Exception{

        try{

            myAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(((JwtRequestDTO) baseDTO).getUsername(), ((JwtRequestDTO) baseDTO).getPassword())
            );

            final UserDetails userDetails = myUserDetailsService.loadUserByUsername(((JwtRequestDTO) baseDTO).getUsername());
            final String jwtToken = jwtTokenProvider.generateToken(userDetails);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Sign in complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .object(new JwtResponseDTO(jwtToken))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTAILS", e);
        } catch (Exception e) {

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

            Account account = accountRepository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());

            if(((AccountDTO) baseDTO).getNewPassword().equals("") || ((AccountDTO) baseDTO).getNewPassword() == null ||
                ((AccountDTO) baseDTO).getAccountPassword().equals("") || ((AccountDTO) baseDTO).getAccountPassword() == null ||
                    ((AccountDTO) baseDTO).getRePassword().equals("") || ((AccountDTO) baseDTO).getRePassword() == null){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input fill field before update password !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }else if (!passwordEncoder.matches(((AccountDTO) baseDTO).getAccountPassword(), account.getAccountPassword())){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Present password is not correct !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }else if ( passwordEncoder.matches(((AccountDTO) baseDTO).getNewPassword(), account.getAccountPassword()) ){

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

            account.setAccountPassword(passwordEncoder.encode(((AccountDTO) baseDTO).getNewPassword()));
            accountRepository.save(account);

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

    @Override
    public ResponseDTO<BaseDTO> updateRole(BaseDTO baseDTO) {

        try{

            Account account = accountRepository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());
            Optional<Role> roleOpt = roleRepository.findById(((AccountDTO) baseDTO).getRole().getRoleId());

            if(!roleOpt.isPresent()){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input role to update !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            account.setRole(roleOpt.get());
            accountRepository.save(account);

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
