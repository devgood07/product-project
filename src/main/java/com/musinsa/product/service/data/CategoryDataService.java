package com.musinsa.product.service.data;

import com.musinsa.product.entity.Category;

public interface CategoryDataService {
    Category findByName(String name);
}
