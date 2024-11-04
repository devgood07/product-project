package com.musinsa.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.product.util.ProductUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 응답하는 DTO")
public class LowestPricePerCategoryResponse {
    @JsonProperty("상품")
    private List<ProductDto> product;
    @JsonProperty("총액")
    private String totalPrice;

    public static LowestPricePerCategoryResponse success(List<ProductDto> productDtos, long totalPrice) {
        return new LowestPricePerCategoryResponse(productDtos, ProductUtils.formatPrice(totalPrice));
    }
}
