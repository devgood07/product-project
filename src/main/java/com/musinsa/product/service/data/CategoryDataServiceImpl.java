package com.musinsa.product.service.data;

import com.musinsa.product.dto.ProductResultCode;
import com.musinsa.product.entity.Category;
import com.musinsa.product.exception.ProductRuntimeException;
import com.musinsa.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryDataServiceImpl implements CategoryDataService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ProductRuntimeException(ProductResultCode.CATEGORY_NOT_FOUND));

    }

}
