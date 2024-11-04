package com.musinsa.product.service.data;

import com.musinsa.product.dto.BrandPriceData;
import com.musinsa.product.dto.ProductResultCode;
import com.musinsa.product.entity.Product;
import com.musinsa.product.exception.ProductRuntimeException;
import com.musinsa.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductDataServiceImpl implements ProductDataService{

    private final ProductRepository productRepository;

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product findProductById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductRuntimeException(ProductResultCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> findProductsByBrandIdWithCategory(Long brandId) {
        return productRepository.findProductsByBrandIdWithCategory(brandId);
    }

    @Override
    public boolean existsByBrandId(Long brandId) {
        return productRepository.existsByBrandId(brandId);
    }

    @Override
    public void deleteProductsByBrand(@Param("brandId") Long brandId) {
        productRepository.deleteProductsByBrand(brandId);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductRuntimeException(ProductResultCode.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> findLowestPriceByCategory(String categoryName) {
        // 최저가 상품 조회
        List<Product> lowestPriceProducts = productRepository.findLowestPriceByCategory(categoryName);

        if (lowestPriceProducts.isEmpty() ) {
            throw new ProductRuntimeException(ProductResultCode.CATEGORY_NOT_FOUND);
        }
        return lowestPriceProducts;
    }

    @Override
    public List<Product> findHighestPriceByCategory(String categoryName) {
        // 최고가 상품 조회
        List<Product> highestPriceProducts = productRepository.findHighestPriceByCategory(categoryName);
        if (highestPriceProducts.isEmpty()) {
            throw new ProductRuntimeException(ProductResultCode.CATEGORY_NOT_FOUND);
        }
        return highestPriceProducts;
    }

    @Override
    public BrandPriceData findBrandWithLowestTotalPrice() {
        BrandPriceData brandData = productRepository.findBrandWithLowestTotalPrice();
        if (brandData == null) {
            throw new ProductRuntimeException(ProductResultCode.BRAND_NOT_FOUND);
        }
        return brandData;
    }

    @Override
    public List<Product> findLowestPricePerCategory() {
        return productRepository.findLowestPricePerCategory();
    }

}
