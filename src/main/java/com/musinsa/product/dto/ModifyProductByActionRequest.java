package com.musinsa.product.dto;

import com.musinsa.product.validation.annotation.ValidAction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidAction
@Schema(description = "브랜드 및 상품 추가 / 업데이트 / 삭제 액션 요청")
public class ModifyProductByActionRequest {
    @NotBlank(message = "Action은 필수입니다.")
    @Schema(description = "1. 추가, 2. 업데이트, 3. 삭제", example = "add_product", allowableValues = {"add_brand", "update_brand", "delete_brand", "add_product", "update_product", "delete_product"})
    private String action;

    @Schema(description = "상품 ID", example = "1")
    private Long productId;
    @Schema(description = "브랜드 ID", example = "1")
    private Long brandId;
    @Schema(description = "카테고리 이름", example = "상의")
    @Size(max = 20, message = "Category name은 최대 20자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "Category name에는 특수 문자나 공백을 사용할 수 없습니다.")
    private String categoryName;

    @Schema(description = "브랜드 이름", example = "A")
    @Size(max = 30, message = "Brand name은 최대 30자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "Brand name에는 특수 문자나 공백을 사용할 수 없습니다.")
    private String brandName;
    @Schema(description = "상품 가격", example = "12000")
    @Pattern(regexp = "^[0-9]+$", message = "Price는 숫자만 입력할 수 있습니다.")
    private String price;

    public ProductDto createProductDto() {
        return new ProductDto(productId, brandId, categoryName, brandName, price);
    }

}

