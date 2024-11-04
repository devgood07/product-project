package com.musinsa.product.service;

import com.musinsa.product.dto.ProductDto;
import com.musinsa.product.dto.ModifyProductByActionRequest;
import com.musinsa.product.dto.ModifyProductByActionResponse;
import org.springframework.transaction.annotation.Transactional;

public interface ProductAdminService {

    @Transactional
    ModifyProductByActionResponse modifyProductByAction(ModifyProductByActionRequest request);

    ModifyProductByActionResponse addBrand(ProductDto productDto);

    ModifyProductByActionResponse deleteBrand(ProductDto productDto);

    ModifyProductByActionResponse updateBrand(ProductDto productDto);

    ModifyProductByActionResponse addProduct(ProductDto productDto);

    ModifyProductByActionResponse updateProduct(ProductDto productDto);

    ModifyProductByActionResponse deleteProduct(ProductDto productDto);
}
