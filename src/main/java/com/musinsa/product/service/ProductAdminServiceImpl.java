package com.musinsa.product.service;

import com.musinsa.product.dto.ModifyProductByActionRequest;
import com.musinsa.product.dto.ModifyProductByActionResponse;
import com.musinsa.product.dto.ProductDto;
import com.musinsa.product.entity.Brand;
import com.musinsa.product.entity.Category;
import com.musinsa.product.entity.Product;
import com.musinsa.product.dto.ProductResultCode;
import com.musinsa.product.exception.ProductRuntimeException;
import com.musinsa.product.service.data.BrandDataService;
import com.musinsa.product.service.data.CategoryDataService;
import com.musinsa.product.service.data.ProductDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductAdminServiceImpl implements ProductAdminService {

    private final ProductDataService productDataService;
    private final CategoryDataService categoryDataService;
    private final BrandDataService brandDataService;

    @Override
    @Transactional
    public ModifyProductByActionResponse modifyProductByAction(ModifyProductByActionRequest request) {
        switch (request.getAction().toLowerCase()) {
            case "add_brand":
                return addBrand(request.createProductDto());
            case "update_brand":
                return updateBrand(request.createProductDto());
            case "delete_brand":
                return deleteBrand(request.createProductDto());
            case "add_product":
                return addProduct(request.createProductDto());
            case "update_product":
                return updateProduct(request.createProductDto());
            case "delete_product":
                return deleteProduct(request.createProductDto());
            default:
                throw new ProductRuntimeException(ProductResultCode.INVALID_INPUT_VALUE);
        }
    }

    @Override
    public ModifyProductByActionResponse addBrand(ProductDto productDto) {
        brandDataService.findByNameOrElseSave(productDto.getBrandName());
        return ModifyProductByActionResponse.success(ProductResultCode.ADD_BRAND_SUCCESS);
    }

    @Override
    public ModifyProductByActionResponse deleteBrand(ProductDto productDto) {
        Long brandId = productDto.getBrandId();

        // 연관된 상품이 있으면 삭제 불가
        boolean hasProducts = productDataService.existsByBrandId(brandId);
        if (hasProducts) {
            throw new ProductRuntimeException(ProductResultCode.PRODUCT_ASSOCIATED_WITH_BRAND);
        }

        brandDataService.deleteBrand(brandId);
        return ModifyProductByActionResponse.success(ProductResultCode.DELETE_BRAND_SUCCESS);
    }

    @Override
    public  ModifyProductByActionResponse updateBrand(ProductDto productDto) {
        brandDataService.findBrandById(productDto.getBrandId())
                .orElseThrow(() -> new ProductRuntimeException(ProductResultCode.BRAND_NOT_FOUND, productDto.getBrandName()));

        Brand brand = new Brand(productDto.getBrandId(), productDto.getBrandName());
        brandDataService.updateBrand(brand);
        return ModifyProductByActionResponse.success(ProductResultCode.UPDATE_BRAND_SUCCESS);
    }

    @Override
    public ModifyProductByActionResponse addProduct(ProductDto productDto) {
        Brand brand = brandDataService.findByNameOrElseSave(productDto.getBrandName()); //브랜드가 없으면 저장
        Category category = categoryDataService.findByName(productDto.getCategoryName()); //카테고리가 없으면 에러 발생 (8개 한정)
        productDataService.addProduct(new Product(brand, category, Long.parseLong(productDto.getPrice()))); //상품 추가
        return ModifyProductByActionResponse.success(ProductResultCode.ADD_PRODUCT_SUCCESS);
    }

    @Override
    public ModifyProductByActionResponse updateProduct(ProductDto productDto) {
        // 기존 상품을 불러옴 (상품이 없으면 에러 발생)
        Product product = productDataService.findProductById(productDto.getProductId());

        // 가격 업데이트
        if (productDto.getPrice() != null) {
            product.setPrice(Long.parseLong(productDto.getPrice()));
        }
        // 카테고리 업데이트
        if (productDto.getCategoryName() != null) {
            // 카테고리가 없으면 에러 발생 (존재하는 8개 카테고리 내로 등록해야함)
            Category category = categoryDataService.findByName(productDto.getCategoryName());
            product.setCategory(category);
        }
        // 브랜드 업데이트
        if (productDto.getBrandName() != null) {
            // 브랜드가 없으면, 브랜드를 등록한뒤, 상품을 업데이트
            Brand brand = brandDataService.findByNameOrElseSave(productDto.getBrandName());
            product.setBrand(brand);
        }
        productDataService.updateProduct(product);

        return ModifyProductByActionResponse.success(ProductResultCode.UPDATE_PRODUCT_SUCCESS);
    }

    @Override
    public ModifyProductByActionResponse deleteProduct(ProductDto productDto) {
        if (productDto.getBrandId() != null) { // 브랜드에 속한 모든 상품 삭제
            productDataService.deleteProductsByBrand(productDto.getBrandId());
            brandDataService.deleteBrand(productDto.getBrandId());

            return ModifyProductByActionResponse.success(ProductResultCode.DELETE_PRODUCT_SUCCESS);
        }

        if (productDto.getProductId() != null) { // 상품 하나만 삭제
            //상품만 삭제되고, 브랜드나 카테고리는 그대로.
            productDataService.deleteProduct(productDto.getProductId());
        }
        return ModifyProductByActionResponse.success(ProductResultCode.DELETE_PRODUCT_SUCCESS);
    }
}
