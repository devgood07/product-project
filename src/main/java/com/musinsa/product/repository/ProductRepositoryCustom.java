package com.musinsa.product.repository;


import com.musinsa.product.dto.BrandPriceData;
import com.musinsa.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findLowestPricePerCategory();
    BrandPriceData findBrandWithLowestTotalPrice();
    List<Product> findProductsByBrandIdWithCategory(Long brandId);
    List<Product> findProductsByBrandId(Long brandId);
    List<Product> findLowestPriceByCategory(String categoryName);
    List<Product> findHighestPriceByCategory(String categoryName);
    boolean existsByBrandId(Long brandId);

}
