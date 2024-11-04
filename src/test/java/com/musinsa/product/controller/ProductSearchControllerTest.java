package com.musinsa.product.controller;

import com.musinsa.product.dto.ProductResultCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *  카테고리/브랜드/상품 정보 조회 API 통합 테스트
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("카테고리별 최저 가격 조회 테스트.")
    @Test
    @Order(1)
    public void testGetLowestPricePerCategory() throws Exception {
        long startTime = System.currentTimeMillis();
        mockMvc.perform(get("/search/api/v1/products/lowest-price-per-category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '상의')].브랜드").value("C"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '상의')].가격").value("10,000"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '아우터')].브랜드").value("E"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '아우터')].가격").value("5,000"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '바지')].브랜드").value("D"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '바지')].가격").value("3,000"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '스니커즈')].브랜드").value("G"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '스니커즈')].가격").value("9,000"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '가방')].브랜드").value("A"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '가방')].가격").value("2,000"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '모자')].브랜드").value("D"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '모자')].가격").value("1,500"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '양말')].브랜드").value("I"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '양말')].가격").value("1,700"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '액세서리')].브랜드").value("F"))
                .andExpect(jsonPath("$.상품[?(@.카테고리 == '액세서리')].가격").value("1,900"))
                .andExpect(jsonPath("$.총액").value("34,100"));
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: "+ (endTime - startTime) + " seconds");
    }

    @DisplayName("단일 브랜드의 카테고리별 최저 가격 및 총액 조회 테스트")
    @Test
    @Order(2)
    public void testGetLowestPricePerBrand() throws Exception {
        mockMvc.perform(get("/search/api/v1/products/lowest-price-per-brand")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.최저가.브랜드").value("D"))
                .andExpect(jsonPath("$.최저가.카테고리[?(@.카테고리 == '상의')].가격").value("10,100"))
                .andExpect(jsonPath("$.최저가.카테고리[?(@.카테고리 == '아우터')].가격").value("5,100"))
                .andExpect(jsonPath("$.최저가.카테고리[?(@.카테고리 == '바지')].가격").value("3,000"))
                .andExpect(jsonPath("$.최저가.카테고리[?(@.카테고리 == '스니커즈')].가격").value("9,500"))
                .andExpect(jsonPath("$.최저가.카테고리[?(@.카테고리 == '가방')].가격").value("2,500"))
                .andExpect(jsonPath("$.최저가.카테고리[?(@.카테고리 == '모자')].가격").value("1,500"))
                .andExpect(jsonPath("$.최저가.카테고리[?(@.카테고리 == '양말')].가격").value("2,400"))
                .andExpect(jsonPath("$.최저가.카테고리[?(@.카테고리 == '액세서리')].가격").value("2,000"))
                .andExpect(jsonPath("$.최저가.총액").value("36,100"));
    }

    @DisplayName("특정 카테고리의 최저 및 최고 가격 조회 테스트")
    @Test
    @Order(3)
    public void testGetPriceByCategory() throws Exception {
        mockMvc.perform(get("/search/api/v1/products/price-by-category/{category}", "상의")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.카테고리").value("상의"))
                .andExpect(jsonPath("$.최저가[0].브랜드").value("C"))
                .andExpect(jsonPath("$.최저가[0].가격").value("10,000"))
                .andExpect(jsonPath("$.최고가[0].브랜드").value("I"))
                .andExpect(jsonPath("$.최고가[0].가격").value("11,400"));
    }

    @DisplayName("존재하지 않는 카테고리로 조회 시 에러 반환 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"없는카테고리1"})
    @Order(4)
    public void testGetPriceByCategory_NotFound(String category) throws Exception {
        mockMvc.perform(get("/search/api/v1/products/price-by-category/{category}", category)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ProductResultCode.CATEGORY_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.CATEGORY_NOT_FOUND.getMessage()));
    }

}
