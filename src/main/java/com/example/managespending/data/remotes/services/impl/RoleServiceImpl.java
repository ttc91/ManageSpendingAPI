package com.example.managespending.data.remotes.services.impl;

import com.example.managespending.data.mappers.RoleMapper;
import com.example.managespending.data.models.dto.RoleDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.entities.Role;
import com.example.managespending.data.remotes.repositories.RoleRepository;
import com.example.managespending.data.remotes.services.RoleService;
import com.example.managespending.data.remotes.services.base.BaseService;
import com.example.managespending.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RoleServiceImpl extends BaseService<BaseDTO> implements RoleService {

    @Autowired
    RoleRepository repository;

    @Autowired
    RoleMapper mapper;

    @Override
    public ResponseDTO<BaseDTO> create(BaseDTO baseDTO) {

        try {

            if (((RoleDTO) baseDTO).getRoleName().length() == 0 || ((RoleDTO) baseDTO).getRoleName() == null) {

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input role name !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Role role = mapper.mapToEntity((RoleDTO) baseDTO, Role.class);
            repository.save(role);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Create role complete !")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .object(mapper.mapToDTO(role, RoleDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Please input role name !")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

}
