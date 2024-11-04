package com.musinsa.product.service.data;

import com.musinsa.product.entity.Brand;

import java.util.Optional;

public interface BrandDataService {
    Brand findByNameOrElseSave(String name);

    void deleteBrand(Long id);

    Optional<Long> findBrandIdByName(String brandName);

    Optional<Brand> findBrandById(Long brandId);

    void updateBrand(Brand brand);
}
