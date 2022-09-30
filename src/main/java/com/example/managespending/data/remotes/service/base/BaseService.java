package com.example.managespending.data.remotes.service.base;

import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;

public abstract class BaseService<T> {

    public ResponseDTO<T> create (BaseDTO baseDTO) { return null; };
    public ResponseDTO<T> update (BaseDTO baseDTO) { return null; };
    public ResponseDTO<T> delete (BaseDTO baseDTO) { return null; };
    public ResponseDTO<T> getOne (BaseDTO baseDTO) { return null; };
    public ResponseDTO<T> getAll () { return null; };
    public ResponseDTO<T> getAll (T t) {return null; };

}
