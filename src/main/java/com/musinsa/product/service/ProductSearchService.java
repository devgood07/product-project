package com.musinsa.product.service;

import com.musinsa.product.dto.LowestPricePerBrandResponse;
import com.musinsa.product.dto.LowestPricePerCategoryResponse;
import com.musinsa.product.dto.PriceByCategoryResponse;

public interface ProductSearchService {
    LowestPricePerCategoryResponse getLowestPricePerCategory();
    LowestPricePerBrandResponse getLowestPricePerBrand();
    PriceByCategoryResponse getPriceByCategory(String categoryName);
}
