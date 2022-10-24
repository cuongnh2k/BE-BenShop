package com.example.bekiashop.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static BaseResponseDto error(String message) {
        return BaseResponseDto.builder()
                .success(false)
                .message(message).build();
    }

    public static BaseResponseDto error(String message, Integer errorCode) {
        return BaseResponseDto.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message).build();
    }

    public static BaseResponseDto error(String message, Integer errorCode, Object data) {
        return BaseResponseDto.builder()
                .success(false)
                .errorCode(errorCode)
                .data(data)
                .message(message).build();
    }

    public static BaseResponseDto success(String message) {
        return BaseResponseDto.builder()
                .success(true)
                .message(message)
                .build();
    }

    public static BaseResponseDto success(Object data) {
        return BaseResponseDto.builder()
                .success(true)
                .data(data)
                .build();
    }


    public static BaseResponseDto success(Object data, String message) {
        return BaseResponseDto.builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }
}
