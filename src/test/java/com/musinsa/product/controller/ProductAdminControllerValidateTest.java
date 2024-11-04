package com.musinsa.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.product.dto.ModifyProductByActionRequest;
import com.musinsa.product.dto.ProductResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * 브랜드 및 상품 수정 API 요청 시 유효성 검사 통합 테스트
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductAdminControllerValidateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Test for invalid action values
    @ParameterizedTest
    @ValueSource(strings = {"unknown", "123"})
    @DisplayName("잘못된 action 값 테스트")
    @Order(1)
    public void testInvalidAction(String action) throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction(action);
        request.setProductId(1L);
        request.setPrice("10000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    @DisplayName("잘못된 productId 값 테스트")
    @Order(2)
    public void testInvalidProductId(long productId) throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_product");
        request.setProductId(productId);
        request.setPrice("15000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    @DisplayName("잘못된 brandId 값 테스트")
    @Order(2)
    public void testInvalidBrandId(long productId) throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_brand");
        request.setBrandId(productId);

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "BrandNameThatIsWayTooLongAndShouldCauseAValidationError",
            "!@#$%^&*()"
    })
    @DisplayName("잘못된 brandName 값 테스트")
    @Order(3)
    public void testInvalidBrandName(String brandName) throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_product");
        request.setBrandName(brandName);
        request.setCategoryName("상의");
        request.setPrice("15000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"NonExistingCategory"})
    @DisplayName("잘못된 categoryName 값 테스트")
    @Order(4)
    public void testInvalidCategoryName(String categoryName) throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_product");
        request.setBrandName("ValidBrand");
        request.setCategoryName(categoryName);
        request.setPrice("15000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ProductResultCode.CATEGORY_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.CATEGORY_NOT_FOUND.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "123abc", "-1000"})
    @DisplayName("잘못된 price 값 테스트")
    @Order(5)
    public void testInvalidPrice(String price) throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_product");
        request.setBrandName("ValidBrand");
        request.setCategoryName("상의");
        request.setPrice(price);

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }
}
