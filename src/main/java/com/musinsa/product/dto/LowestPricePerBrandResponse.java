package com.musinsa.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.product.util.ProductUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 응답하는 DTO")
public class LowestPricePerBrandResponse {
    public static final LowestPricePerBrandResponse EMPTY = new LowestPricePerBrandResponse(new LowestPricePerBrandDto("", List.of(), ""));
    @JsonProperty("최저가")
    private LowestPricePerBrandDto lowestPrice;

    public static LowestPricePerBrandResponse success(String brandName, List<ProductDto> categoryDetails, long totalPrice) {
        return new LowestPricePerBrandResponse(new LowestPricePerBrandDto(brandName, categoryDetails, ProductUtils.formatPrice(totalPrice)));
    }
}

