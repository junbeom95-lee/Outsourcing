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

        HashMap<String, String> message = getErrorMessage(e);

        CommonResponse<HashMap<String, String>> response = new CommonResponse<>(false, "올바르지 않은 입력입니다.", message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    // Valid 검증 에러들 HashMap<filed, message>으로 생성
    private HashMap<String, String> getErrorMessage(MethodArgumentNotValidException e) {

        //1. Valid로 검증한 모든 에러들
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        HashMap<String, String> map = new HashMap<>();

        //2. 에러들 각각 map에 (key : 필드, value : message) 담기
        allErrors.forEach(error -> {

            if (error instanceof FieldError fieldError) {
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();

                map.put(field, message);
            }
        });
        return map;
    }
}
