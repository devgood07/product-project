package com.musinsa.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 추가 / 업데이트 / 삭제 액션 응답 DTO")
public class ModifyProductByActionResponse {

    @Schema(description = "응답 코드", example = "S001")
    private String code;
    @Schema(description = "응답 메시지", example = "브랜드가 성공적으로 추가되었습니다.")
    private String message;

    public static ModifyProductByActionResponse success(ProductResultCode productResultCode) {
        return new ModifyProductByActionResponse(productResultCode.getCode(), productResultCode.getMessage());
    }
}
