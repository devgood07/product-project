package com.musinsa.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 응답하는 DTO")
public class PriceByCategoryResponse {
    @JsonProperty("카테고리")
    private String category;
    @JsonProperty("최저가")
    private List<ProductDto> lowestPrice;
    @JsonProperty("최고가")
    private List<ProductDto> highestPrice;

    public static PriceByCategoryResponse success(String category, List<ProductDto> lowestPrice, List<ProductDto> highestPrice) {
        return new PriceByCategoryResponse(category, lowestPrice, highestPrice);
    }
}
