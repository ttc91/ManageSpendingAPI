package com.example.managespending.data.models.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T>{

    private String message;

    private Integer statusCode;

    private T object;

    private List<?> objectList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

}
