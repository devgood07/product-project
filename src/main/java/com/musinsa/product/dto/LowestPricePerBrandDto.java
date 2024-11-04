package com.musinsa.product.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LowestPricePerBrandDto {
    @JsonProperty("브랜드")
    private String brand;
    @JsonProperty("카테고리")
    private List<ProductDto> categories;
    @JsonProperty("총액")
    private String totalPrice;
}