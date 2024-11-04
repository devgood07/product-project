package com.musinsa.product.service;

import com.musinsa.product.dto.BrandPriceData;
import com.musinsa.product.dto.LowestPricePerBrandResponse;
import com.musinsa.product.dto.LowestPricePerCategoryResponse;
import com.musinsa.product.dto.PriceByCategoryResponse;
import com.musinsa.product.entity.Brand;
import com.musinsa.product.entity.Category;
import com.musinsa.product.entity.Product;
import com.musinsa.product.service.data.ProductDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ProductSearchServiceImplTest {

    @Mock
    private ProductDataService productDataService;

    @InjectMocks
    private ProductSearchServiceImpl ProductSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("카테고리별 최저 가격 조회")
    void testGetLowestPricePerCategory() {
        Product product1 = new Product();
        product1.setPrice(10000);
        product1.setCategory(new Category(1L, "상의"));
        product1.setBrand(new Brand("C"));

        Product product2 = new Product();
        product2.setPrice(5000);
        product2.setCategory(new Category(2L, "아우터"));
        product2.setBrand(new Brand("E"));

        when(productDataService.findLowestPricePerCategory()).thenReturn(Arrays.asList(product1, product2));

        LowestPricePerCategoryResponse response = ProductSearchService.getLowestPricePerCategory();

        assertNotNull(response);
        assertEquals(2, response.getProduct().size());
        assertEquals("15,000", response.getTotalPrice());
    }

    @Test
    @DisplayName("최저가 브랜드 조회")
    void testGetLowestPricePerBrand() {
        BrandPriceData brandData = new BrandPriceData(1L, "C", 15000L);
        Product product1 = new Product();
        product1.setPrice(10000);
        product1.setCategory(new Category(1L, "상의"));
        Product product2 = new Product();
        product2.setPrice(5000);
        product2.setCategory(new Category(2L, "아우터"));

        when(productDataService.findBrandWithLowestTotalPrice()).thenReturn(brandData);
        when(productDataService.findProductsByBrandIdWithCategory(brandData.getBrandId()))
                .thenReturn(Arrays.asList(product1, product2));

        LowestPricePerBrandResponse response = ProductSearchService.getLowestPricePerBrand();

        assertNotNull(response);
        assertEquals("C", response.getLowestPrice().getBrand());
        assertEquals("15,000", response.getLowestPrice().getTotalPrice());
        assertEquals(2, response.getLowestPrice().getCategories().size());
    }

    @Test
    @DisplayName("카테고리별 최저가 및 최고가 상품 조회")
    void testGetPriceByCategory() {
        Product lowestPriceProduct = new Product();
        lowestPriceProduct.setPrice(5000);
        lowestPriceProduct.setBrand(new Brand("A"));

        Product highestPriceProduct = new Product();
        highestPriceProduct.setPrice(15000);
        highestPriceProduct.setBrand(new Brand("B"));

        when(productDataService.findLowestPriceByCategory("상의"))
                .thenReturn(Collections.singletonList(lowestPriceProduct));
        when(productDataService.findHighestPriceByCategory("상의"))
                .thenReturn(Collections.singletonList(highestPriceProduct));

        PriceByCategoryResponse response = ProductSearchService.getPriceByCategory("상의");

        assertNotNull(response);
        assertEquals("상의", response.getCategory());
        assertEquals("5,000", response.getLowestPrice().get(0).getPrice());
        assertEquals("15,000", response.getHighestPrice().get(0).getPrice());
    }
}
