package com.musinsa.product.service.data;

import com.musinsa.product.entity.Brand;
import com.musinsa.product.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class BrandDataServiceImpl implements BrandDataService {
    private final BrandRepository brandRepository;

    @Override
    public Brand findByNameOrElseSave(String brandName) {
        return brandRepository.findByName(brandName)
                .orElseGet(() -> brandRepository.save(new Brand(brandName)));
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Optional<Long> findBrandIdByName(String brandName) {
        return brandRepository.findByName(brandName).map(Brand::getId);
    }

    @Override
    public Optional<Brand> findBrandById(Long brandId) {
        return brandRepository.findById(brandId);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandRepository.save(brand);
    }
}
