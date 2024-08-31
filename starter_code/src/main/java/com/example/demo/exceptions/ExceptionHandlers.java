package com.example.demo.exceptions;

import com.example.demo.model.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<BaseResponse<String>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(false, e.getMessage()));
    }

    @ExceptionHandler(OrderCreationException.class)
    public ResponseEntity<BaseResponse<String>> handleOrderCreationException(OrderCreationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(false, e.getMessage()));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleItemNotFoundException(ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, ex.getMessage()));
    }

    @ExceptionHandler(UserPasswordException.class)
    public ResponseEntity<BaseResponse<String>> handleUserPasswordException(UserPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(false, ex.getMessage()));
    }
}
