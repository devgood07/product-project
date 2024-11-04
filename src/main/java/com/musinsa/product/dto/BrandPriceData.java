package com.musinsa.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandPriceData {
    private Long brandId;
    private String brandName;
    private Long totalPrice;
}
