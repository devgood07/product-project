package com.musinsa.product.exception;

import com.musinsa.product.dto.ProductResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRuntimeException extends RuntimeException {
    private final ProductResultCode errorCode;
    public ProductRuntimeException(ProductResultCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ProductRuntimeException(ProductResultCode errorCode, String message) {
        super(String.format("[%s] %s", errorCode.getMessage(), message));
        this.errorCode = errorCode;
    }
}
