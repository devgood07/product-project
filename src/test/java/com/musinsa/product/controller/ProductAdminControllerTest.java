package com.musinsa.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.product.dto.ModifyProductByActionRequest;
import com.musinsa.product.dto.ProductResultCode;
import com.musinsa.product.repository.BrandRepository;
import com.musinsa.product.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//주석 설명 추가
//read me 작성
//라인 맞추기.
//git hub

/**
 *  브랜드 및 상품 수정 API 통합 테스트
 */
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /*
    {
        "action": "add_brand",
        "brandName": "X"
    }
    */
    @Test
    @DisplayName("브랜드 추가 성공 - 필수 필드 제공")
    @Order(1)
    public void testAddBrandSuccess() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_brand");
        request.setBrandName("X");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.ADD_BRAND_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.ADD_BRAND_SUCCESS.getMessage()));
    }


    @Test
    @DisplayName("브랜드 추가 실패 - brandName 누락")
    @Order(1)
    public void testAddMissingBrandName() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_brand");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /*
    {
      "action": "update_brand",
      "brandId": 1,
      "brandName": "NEWBRANDNAME",
    }
    */
    @Test
    @DisplayName("브랜드 업데이트 성공 - 필수 필드 제공")
    @Order(1)
    public void testUpdateBrandSuccess() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_brand");
        request.setBrandId(1l);
        request.setBrandName("NEWBRANDNAME");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.UPDATE_BRAND_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.UPDATE_BRAND_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("브랜드 업데이트 실패 - brandName 누락")
    @Order(1)
    public void testUpdateMissingBrandName() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_brand");
        request.setBrandId(1l);

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /*
    {
      "action": "delete_brand",
      "brandId": 1,
    }
    */
    @Test
    @DisplayName("브랜드 삭제 실패 - 연관된 상품 존재")
    @Order(1)
    public void testDeleteBrandProductAssociated() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("delete_brand");
        request.setBrandId(1l);

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.PRODUCT_ASSOCIATED_WITH_BRAND.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.PRODUCT_ASSOCIATED_WITH_BRAND.getMessage()));
    }

    @Test
    @DisplayName("브랜드 삭제 실패 - id 누락")
    @Order(1)
    public void testDeleteMissingBrandId() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("delete_brand");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /*
    {
        "action": "add_product",
        "categoryName": "상의",
        "brandName": "A",
        "price": "12000"
    }
     */
    @Test
    @DisplayName("상품 추가 성공 - 모든 필수 필드 제공")
    @Order(1)
    public void testAddProductSuccess() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_product");
        request.setBrandName("A");
        request.setCategoryName("상의");
        request.setPrice("15000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.ADD_PRODUCT_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.ADD_PRODUCT_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("상품 추가 실패 - brandName 누락")
    @Order(2)
    public void testAddProductMissingBrandName() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_product");
        request.setCategoryName("상의");
        request.setPrice("15000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    @Test
    @DisplayName("상품 추가 실패 - categoryName 누락")
    @Order(3)
    public void testAddProductMissingCategoryName() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_product");
        request.setBrandName("C");
        request.setPrice("15000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    @Test
    @DisplayName("상품 추가 실패 - price 누락")
    @Order(4)
    public void testAddProductMissingPrice() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("add_product");
        request.setBrandName("C");
        request.setCategoryName("상의");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /*
    {
       "action": "update_product",
       "productId": 1,
       "price": "20000"
    }
     */
    @Test
    @DisplayName("상품 업데이트 성공 - productId 제공")
    @Order(5)
    public void testUpdateProductSuccess() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_product");
        request.setProductId(1L);
        request.setPrice("20000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getMessage()));
    }

    /*
    {
       "action": "update_product",
       "productId": 1,
       "categoryName": "바지"
    }
     */
    @Test
    @DisplayName("상품 카테고리 업데이트 성공 - productId 제공")
    @Order(5)
    public void testUpdateProductCategorySuccess() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_product");
        request.setProductId(1L);
        request.setCategoryName("바지");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getMessage()));
    }

    /*
    {
       "action": "update_product",
       "productId": 1,
       "brandName": "UPDATERANDNAME"
    }
     */
    @Test
    @DisplayName("상품 신규 추가 브랜드 업데이트 성공 - productId 제공")
    @Order(5)
    public void testUpdateProductBrandSuccess() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_product");
        request.setProductId(1L);
        request.setBrandName("UPDATERANDNAME");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getMessage()));
    }

    /*
    {
       "action": "update_product",
       "productId": 1,
       "brandName": "I"
    }
     */
    @Test
    @DisplayName("상품 신규 추가 브랜드 업데이트 성공 - productId 제공")
    @Order(5)
    public void testUpdateProductOldBrandSuccess() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_product");
        request.setProductId(1L);
        request.setBrandName("I");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getMessage()));
    }


    /*
    {
      "action": "update_product",
      "productId": 1,
      "categoryName": "모자",
      "brandName": "C",
      "price": "100"
    }
     */
    @Test
    @DisplayName("상품 모든 항목 업데이트 성공 - productId 제공")
    @Order(5)
    public void testUpdateProductAllSuccess() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_product");
        request.setProductId(1L);
        request.setCategoryName("모자");
        request.setBrandName("C");
        request.setPrice("100");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("상품 업데이트 실패 - productId 누락")
    @Order(6)
    public void testUpdateProductMissingProductId() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("update_product");
        request.setPrice("20000");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /*
    {
       "action": "delete_product",
       "productId": 1
    }
     */
    @Test
    @DisplayName("상품 삭제 성공 - productId로 삭제")
    @Order(7)
    public void testDeleteProductSuccessWithProductId() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("delete_product");
        request.setProductId(1L);

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.DELETE_PRODUCT_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.DELETE_PRODUCT_SUCCESS.getMessage()));

        brandRepository.findAll().stream().forEach(brand -> {
            System.out.println(brand.getName());
        });

        productRepository.findById(1l).stream().forEach(product -> {
            System.out.println(product.getId());
        });
    }

    /*
    {
       "action": "delete_product",
       "brandId": 2
    }
     */
    @Test
    @DisplayName("상품 삭제 성공 - brandId로 삭제")
    @Order(8)
    public void testDeleteProductSuccessWithBrandId() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("delete_product");
        request.setBrandId(2l);

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ProductResultCode.DELETE_PRODUCT_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.DELETE_PRODUCT_SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("상품 삭제 실패 - productId와 brandId 누락")
    @Order(9)
    public void testDeleteProductMissingProductIdAndBrandId() throws Exception {
        ModifyProductByActionRequest request = new ModifyProductByActionRequest();
        request.setAction("delete_product");

        mockMvc.perform(post("/admin/api/v1/products/modify-product-by-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ProductResultCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ProductResultCode.INVALID_INPUT_VALUE.getMessage()));
    }
}
