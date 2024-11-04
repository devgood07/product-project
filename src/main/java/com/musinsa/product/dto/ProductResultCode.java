package com.musinsa.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ProductResultCode {
    // 성공 코드
    ADD_BRAND_SUCCESS("S001", "브랜드가 성공적으로 추가되었습니다.", HttpStatus.OK),
    DELETE_BRAND_SUCCESS("S002", "브랜드가 성공적으로 삭제되었습니다.", HttpStatus.OK),
    UPDATE_BRAND_SUCCESS("S003", "브랜드가 성공적으로 업데이트되었습니다.", HttpStatus.OK),
    ADD_PRODUCT_SUCCESS("S004", "상품이 성공적으로 추가되었습니다.", HttpStatus.OK),
    DELETE_PRODUCT_SUCCESS("S005", "상품이 성공적으로 삭제되었습니다.", HttpStatus.OK),
    UPDATE_PRODUCT_SUCCESS("S006", "상품이 성공적으로 업데이트되었습니다.", HttpStatus.OK),

    // 클라이언트 오류 코드
    PRODUCT_NOT_FOUND("E001", "상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    BRAND_NOT_FOUND("E002", "브랜드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("E003", "카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_ASSOCIATED_WITH_BRAND("E004", "브랜드에 연관된 상품이 있어서 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST),

    INVALID_INPUT_VALUE("E100", "입력값이 잘못 되었습니다.", HttpStatus.BAD_REQUEST),

    // 서버 오류 코드
    INTERNAL_SERVER_ERROR("E500", "서버에서 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
