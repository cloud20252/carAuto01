package com.spring.jwt.exception;

import com.spring.jwt.Appointment.ResponseDto;
import com.spring.jwt.VehicleReg.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundExceptions.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(UserNotFoundExceptions ex) {
        ResponseDto response = new ResponseDto();
        response.setMessage("Error");
        response.setException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto<Object>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.error("Unexpected error", ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(BadRequestException ex) {
        ResponseDto response = new ResponseDto();
        response.setMessage("Error");
        response.setException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
