package com.musinsa.product.service;

import com.musinsa.product.dto.ProductDto;
import com.musinsa.product.dto.ModifyProductByActionRequest;
import com.musinsa.product.dto.ModifyProductByActionResponse;
import com.musinsa.product.dto.ProductResultCode;
import com.musinsa.product.entity.Brand;
import com.musinsa.product.entity.Category;
import com.musinsa.product.entity.Product;
import com.musinsa.product.exception.ProductRuntimeException;
import com.musinsa.product.service.data.BrandDataService;
import com.musinsa.product.service.data.CategoryDataService;
import com.musinsa.product.service.data.ProductDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AdministratorProductServiceImplTest {

    @Mock
    private ProductDataService productDataService;

    @Mock
    private CategoryDataService categoryDataService;

    @Mock
    private BrandDataService brandDataService;

    @InjectMocks
    private ProductAdminServiceImpl administratorProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("브랜드 추가 - 성공 시나리오")
    void testAddBrandSuccess() {
        ProductDto productDto = ProductDto.builder().brandName("NewBrand").build();
        when(brandDataService.findByNameOrElseSave("NewBrand")).thenReturn(new Brand("NewBrand"));

        ModifyProductByActionResponse response = administratorProductService.addBrand(productDto);

        assertEquals(ProductResultCode.ADD_BRAND_SUCCESS.getCode(), response.getCode());
        verify(brandDataService, times(1)).findByNameOrElseSave("NewBrand");
    }

    @Test
    @DisplayName("브랜드 업데이트 - 성공 시나리오")
    void testUpdateBrandSuccess() {
        ProductDto productDto = ProductDto.builder().brandId(1L).brandName("UpdatedBrand").build();
        when(brandDataService.findBrandById(1L)).thenReturn(Optional.of(new Brand(1L, "OldBrand")));
        doNothing().when(brandDataService).updateBrand(any(Brand.class));

        ModifyProductByActionResponse response = administratorProductService.updateBrand(productDto);

        assertEquals(ProductResultCode.UPDATE_BRAND_SUCCESS.getCode(), response.getCode());
        verify(brandDataService, times(1)).findBrandById(1L);
        verify(brandDataService, times(1)).updateBrand(any(Brand.class));
    }

    @Test
    @DisplayName("브랜드 업데이트 - 존재하지 않는 브랜드로 인한 예외 발생")
    void testUpdateBrandNotFound() {
        ProductDto productDto = ProductDto.builder().brandId(1L).brandName("NonExistingBrand").build();
        when(brandDataService.findBrandById(1L)).thenReturn(Optional.empty());

        ProductRuntimeException exception = assertThrows(ProductRuntimeException.class, () ->
                administratorProductService.updateBrand(productDto)
        );

        assertEquals(ProductResultCode.BRAND_NOT_FOUND.getCode(), exception.getErrorCode().getCode());
        verify(brandDataService, times(1)).findBrandById(1L);
        verify(brandDataService, never()).updateBrand(any(Brand.class));
    }

    @Test
    @DisplayName("브랜드 삭제 - 성공 시나리오")
    void testDeleteBrandSuccess() {
        ProductDto productDto = ProductDto.builder().brandId(1L).build();
        when(productDataService.existsByBrandId(1L)).thenReturn(false);
        doNothing().when(brandDataService).deleteBrand(1L);

        ModifyProductByActionResponse response = administratorProductService.deleteBrand(productDto);

        assertEquals(ProductResultCode.DELETE_BRAND_SUCCESS.getCode(), response.getCode());
        verify(brandDataService, times(1)).deleteBrand(1L);
    }

    @Test
    @DisplayName("브랜드 삭제 - 연관된 상품으로 인한 삭제 실패")
    void testDeleteBrandWithAssociatedProducts() {
        ProductDto productDto = ProductDto.builder().brandId(1L).build();
        when(productDataService.existsByBrandId(1L)).thenReturn(true);

        ProductRuntimeException exception = assertThrows(ProductRuntimeException.class, () ->
                administratorProductService.deleteBrand(productDto)
        );

        assertEquals(ProductResultCode.PRODUCT_ASSOCIATED_WITH_BRAND.getCode(), exception.getErrorCode().getCode());
        verify(brandDataService, never()).deleteBrand(1L);
    }

    @Test
    @DisplayName("상품 추가 - 성공 시나리오")
    void testAddProductSuccess() {
        ProductDto productDto = ProductDto.builder().brandName("TestBrand").categoryName("TestCategory").price("1000").build();
        when(brandDataService.findByNameOrElseSave("TestBrand")).thenReturn(new Brand("TestBrand"));
        when(categoryDataService.findByName("TestCategory")).thenReturn(new Category("TestCategory"));
        doNothing().when(productDataService).addProduct(any(Product.class));

        ModifyProductByActionResponse response = administratorProductService.addProduct(productDto);

        assertEquals(ProductResultCode.ADD_PRODUCT_SUCCESS.getCode(), response.getCode());
        verify(productDataService, times(1)).addProduct(any(Product.class));
    }

    @Test
    @DisplayName("상품 업데이트 - 성공 시나리오")
    void testUpdateProductSuccess() {
        ProductDto productDto = ProductDto.builder().productId(1L).brandName("UpdatedBrand").categoryName("UpdatedCategory").price("1500").build();
        Product existingProduct = new Product();

        when(productDataService.findProductById(1L)).thenReturn(existingProduct);
        when(brandDataService.findByNameOrElseSave("UpdatedBrand")).thenReturn(new Brand("UpdatedBrand"));
        when(categoryDataService.findByName("UpdatedCategory")).thenReturn(new Category("UpdatedCategory"));

        ModifyProductByActionResponse response = administratorProductService.updateProduct(productDto);

        assertEquals(ProductResultCode.UPDATE_PRODUCT_SUCCESS.getCode(), response.getCode());
        verify(productDataService, times(1)).updateProduct(existingProduct);
    }

    @Test
    @DisplayName("상품 삭제 - 브랜드 전체 삭제")
    void testDeleteProductsByBrand() {
        ProductDto productDto = ProductDto.builder().brandId(1L).build();
        doNothing().when(productDataService).deleteProductsByBrand(1L);
        doNothing().when(brandDataService).deleteBrand(1L);

        ModifyProductByActionResponse response = administratorProductService.deleteProduct(productDto);

        assertEquals(ProductResultCode.DELETE_PRODUCT_SUCCESS.getCode(), response.getCode());
        verify(productDataService, times(1)).deleteProductsByBrand(1L);
        verify(brandDataService, times(1)).deleteBrand(1L);
    }

    @Test
    @DisplayName("상품 삭제 - 단일 상품 삭제")
    void testDeleteSingleProduct() {
        ProductDto productDto = ProductDto.builder().productId(1L).build();
        doNothing().when(productDataService).deleteProduct(1L);

        ModifyProductByActionResponse response = administratorProductService.deleteProduct(productDto);

        assertEquals(ProductResultCode.DELETE_PRODUCT_SUCCESS.getCode(), response.getCode());
        verify(productDataService, times(1)).deleteProduct(1L);
    }

    @Test
    @DisplayName("유효하지 않은 액션 - 예외 발생")
    void testInvalidAction() {
        ModifyProductByActionRequest request = ModifyProductByActionRequest.builder().action("invalid_action").build();

        ProductRuntimeException exception = assertThrows(ProductRuntimeException.class, () ->
                administratorProductService.modifyProductByAction(request)
        );

        assertEquals(ProductResultCode.INVALID_INPUT_VALUE.getCode(), exception.getErrorCode().getCode());
    }
}
