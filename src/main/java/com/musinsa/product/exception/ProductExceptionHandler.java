package com.musinsa.product.exception;

import com.musinsa.product.dto.ProductResultCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Order(1)
public class ProductExceptionHandler {

    @ExceptionHandler(ProductRuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleProductRuntimeException(ProductRuntimeException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Map.of(
                        "code", e.getErrorCode().getCode(),
                        "message", e.getErrorCode().getMessage()
                ));
    }

    // 유효성 검사 실패 시 발생하는 MethodArgumentNotValidException 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "code", ProductResultCode.INVALID_INPUT_VALUE.getCode(),
                        "message", ProductResultCode.INVALID_INPUT_VALUE.getMessage()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "code", ProductResultCode.INTERNAL_SERVER_ERROR.getCode(),
                        "message", e.getMessage()
                ));
    }
}
