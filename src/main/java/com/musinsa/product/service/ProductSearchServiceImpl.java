package com.musinsa.product.service;

import com.musinsa.product.dto.*;
import com.musinsa.product.dto.LowestPricePerBrandResponse;
import com.musinsa.product.dto.LowestPricePerCategoryResponse;
import com.musinsa.product.dto.PriceByCategoryResponse;
import com.musinsa.product.dto.ProductDto;
import com.musinsa.product.entity.Product;
import com.musinsa.product.service.data.ProductDataService;
import com.musinsa.product.util.ProductUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductDataService productDataService;

    public LowestPricePerCategoryResponse getLowestPricePerCategory() {
        Map<String, ProductDto> productMap = new HashMap<>();
        long totalPrice = productDataService.findLowestPricePerCategory().stream()
                .filter(product -> {
                    String categoryName = product.getCategory().getName();
                    // 이미 존재하는 카테고리는 총액에 포함하지 않음
                    boolean isNewCategory = !productMap.containsKey(categoryName);

                    // 새로운 카테고리이거나 중복된 경우에는 Map에 상품 정보 저장
                    productMap.put(categoryName, ProductDto.builder()
                            .categoryName(categoryName)
                            .brandName(product.getBrand().getName())
                            .price(ProductUtils.formatPrice(product.getPrice()))
                            .build());

                    return isNewCategory;
                })
                .mapToLong(Product::getPrice)
                .sum();

        List<ProductDto> productDtos = new ArrayList<>(productMap.values());

        return LowestPricePerCategoryResponse.success(productDtos, totalPrice);
    }

    public LowestPricePerBrandResponse getLowestPricePerBrand() {
        BrandPriceData brandData = productDataService.findBrandWithLowestTotalPrice();
        if (brandData == null) {
            return LowestPricePerBrandResponse.EMPTY;
        }

        List<Product> products = productDataService.findProductsByBrandIdWithCategory(brandData.getBrandId());

        List<ProductDto> categoryDetails = products.stream()
                .map(product -> ProductDto.builder()
                        .categoryName(product.getCategory().getName())
                        .price(ProductUtils.formatPrice(product.getPrice()))
                        .build())
                .collect(Collectors.toList());

        return LowestPricePerBrandResponse.success(brandData.getBrandName(), categoryDetails, brandData.getTotalPrice());
    }

    public PriceByCategoryResponse getPriceByCategory(String categoryName) {
        List<Product> lowestPriceProducts = productDataService.findLowestPriceByCategory(categoryName);
        List<Product> highestPriceProducts = productDataService.findHighestPriceByCategory(categoryName);

        List<ProductDto> lowestPrices = lowestPriceProducts.stream()
                .map(product -> ProductDto.builder()
                        .brandName(product.getBrand().getName())
                        .price(ProductUtils.formatPrice(product.getPrice()))
                        .build())
                .collect(Collectors.toList());

        List<ProductDto> highestPrices = highestPriceProducts.stream()
                .map(product -> ProductDto.builder()
                        .brandName(product.getBrand().getName())
                        .price(ProductUtils.formatPrice(product.getPrice()))
                        .build())
                .collect(Collectors.toList());

        return PriceByCategoryResponse.success(categoryName, lowestPrices, highestPrices);
    }

}
