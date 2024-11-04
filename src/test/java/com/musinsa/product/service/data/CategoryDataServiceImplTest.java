package com.musinsa.product.service.data;

import com.musinsa.product.dto.ProductResultCode;
import com.musinsa.product.entity.Category;
import com.musinsa.product.exception.ProductRuntimeException;
import com.musinsa.product.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryDataServiceImplTest {

    @Autowired
    private CategoryDataServiceImpl categoryDataService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 이름으로 조회 성공 테스트")
    void findByName_Success() {
        // Given
        String categoryName = "상의";  // data-test.sql에 있는 카테고리 이름

        // When
        Category result = categoryDataService.findByName(categoryName);

        // Then
        assertEquals(categoryName, result.getName());
    }

    @Test
    @DisplayName("카테고리 이름으로 조회 실패 테스트 - CATEGORY_NOT_FOUND 예외 발생")
    void findByName_CategoryNotFound() {
        // Given
        String categoryName = "없는카테고리";

        // When & Then
        ProductRuntimeException exception = assertThrows(ProductRuntimeException.class, () -> {
            categoryDataService.findByName(categoryName);
        });
        assertEquals(ProductResultCode.CATEGORY_NOT_FOUND, exception.getErrorCode());
    }
}
