package com.musinsa.product.service.data;

import com.musinsa.product.dto.BrandPriceData;
import com.musinsa.product.entity.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDataService {

    void addProduct(Product product);

    void deleteProductsByBrand(@Param("brandId") Long brandId);

    void updateProduct(Product product);

    void deleteProduct(long productId);

    List<Product> findLowestPriceByCategory(String categoryName);

    List<Product> findHighestPriceByCategory(String categoryName);

    BrandPriceData findBrandWithLowestTotalPrice();

    List<Product> findLowestPricePerCategory();

    Product findProductById(long productId);

    List<Product> findProductsByBrandIdWithCategory(Long brandId);

    boolean existsByBrandId(Long brandId);
}
