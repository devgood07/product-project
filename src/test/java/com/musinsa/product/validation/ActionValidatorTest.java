package com.musinsa.product.validation;

import com.musinsa.product.dto.ModifyProductByActionRequest;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActionValidatorTest {

    private ActionValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new ActionValidator();
    }

    @Test
    @DisplayName("브랜드 추가 액션 - 유효한 요청")
    public void testValidAddBrandAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("add_brand")
                .brandName("TestBrand")
                .build();
        assertTrue(validator.isValid(request, context));
    }

    @Test
    @DisplayName("브랜드 추가 액션 - 브랜드 이름 누락")
    public void testInvalidAddBrandAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("add_brand")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 추가 액션 - 유효한 요청")
    public void testValidAddProductAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("add_product")
                .brandName("TestBrand")
                .categoryName("TestCategory")
                .price("1000")
                .build();
        assertTrue(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 추가 액션 - 필드 누락")
    public void testInvalidAddProductAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("add_product")
                .brandName("TestBrand")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 업데이트 액션 - 유효한 요청")
    public void testValidUpdateProductAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("update_product")
                .productId(1L)
                .price("1200")
                .build();
        assertTrue(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 업데이트 액션 - 잘못된 상품 ID")
    public void testInvalidUpdateProductAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("update_product")
                .productId(-1L)
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 삭제 액션 - 유효한 요청(상품 ID로 삭제)")
    public void testValidDeleteProductWithId() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("delete_product")
                .productId(1L)
                .build();
        assertTrue(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 삭제 액션 - 유효한 요청(브랜드 이름으로 삭제)")
    public void testValidDeleteProductWithBrandName() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("delete_product")
                .brandId(1L)
                .build();
        assertTrue(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 삭제 액션 - 상품 ID와 브랜드 이름 모두 누락")
    public void testInvalidDeleteProductAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("delete_product")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("브랜드 추가 - 유효하지 않은 액션명")
    public void testInvalidAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("invalid_action")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("브랜드 추가 - 필수 필드 누락")
    public void testAddBrandMissingName() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("add_brand")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("브랜드 업데이트 - 브랜드 ID가 0일 때")
    public void testUpdateBrandWithZeroId() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("update_brand")
                .brandId(0L)
                .brandName("TestBrand")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("브랜드 업데이트 - 브랜드 이름이 없을 때")
    public void testUpdateBrandMissingName() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("update_brand")
                .brandId(1L)
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("브랜드 삭제 - 브랜드 ID가 음수일 때")
    public void testDeleteBrandWithNegativeId() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("delete_brand")
                .brandId(-1L)
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 추가 - 가격이 음수일 때")
    public void testAddProductNegativePrice() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("add_product")
                .brandName("TestBrand")
                .categoryName("TestCategory")
                .price("-100")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 추가 - 가격이 숫자가 아닐 때")
    public void testAddProductInvalidPriceFormat() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("add_product")
                .brandName("TestBrand")
                .categoryName("TestCategory")
                .price("price")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 업데이트 - 잘못된 가격 포맷")
    public void testUpdateProductInvalidPriceFormat() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("update_product")
                .productId(1L)
                .price("invalid")
                .build();
        assertFalse(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 삭제 - productId 및 brandId 모두 제공")
    public void testDeleteProductBothIdAndBrandId() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("delete_product")
                .productId(1L)
                .brandId(2L)
                .build();
        assertTrue(validator.isValid(request, context));
    }

    @Test
    @DisplayName("상품 삭제 - 필수 필드 누락 (productId와 brandId 모두 없을 때)")
    public void testDeleteProductMissingBothIds() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder()
                .action("delete_product")
                .build();
        assertFalse(validator.isValid(request, context));
    }
}
