package com.example.outsourcing.common.exception;

import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.model.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<Void>> customException(CustomException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();

        CommonResponse<Void> response = new CommonResponse<>(false, exceptionCode.getMessage(), null);

        return ResponseEntity.status(exceptionCode.getStatus()).body(response);
    }

    //Valid 검증 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<HashMap<String, String>>> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        CommonResponse<HashMap<String, String>> response = new CommonResponse<>(false, message, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
