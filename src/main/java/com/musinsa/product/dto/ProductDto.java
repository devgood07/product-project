package com.musinsa.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @JsonIgnore
    private Long productId;
    @JsonIgnore
    private Long brandId;
    @JsonProperty("카테고리")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String categoryName;
    @JsonProperty("브랜드")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String brandName;
    @JsonProperty("가격")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String price;
}
