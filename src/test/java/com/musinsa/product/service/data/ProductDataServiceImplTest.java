package com.musinsa.product.service.data;

import com.musinsa.product.dto.BrandPriceData;
import com.musinsa.product.dto.ProductResultCode;
import com.musinsa.product.entity.Brand;
import com.musinsa.product.entity.Category;
import com.musinsa.product.entity.Product;
import com.musinsa.product.exception.ProductRuntimeException;
import com.musinsa.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductDataServiceImplTest {

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 추가 테스트")
    void testAddProduct() {
        Product newProduct = new Product();
        newProduct.setBrand(new Brand(1L, "A"));
        newProduct.setCategory(new Category(1L, "상의"));
        newProduct.setPrice(15000L);

        productDataService.addProduct(newProduct);

        assertTrue(productRepository.existsById(newProduct.getId()));
    }

    @Test
    @DisplayName("상품 ID로 상품 조회 - 존재하는 상품")
    void testFindProductByIdExists() {
        Product product = productRepository.findById(1L).orElseThrow();

        Product foundProduct = productDataService.findProductById(product.getId());

        assertEquals(product, foundProduct);
    }

    @Test
    @DisplayName("상품 ID로 상품 조회 - 존재하지 않는 상품")
    void testFindProductByIdNotExists() {
        long nonExistentId = 999L;

        ProductRuntimeException exception = assertThrows(ProductRuntimeException.class, () ->
                productDataService.findProductById(nonExistentId)
        );

        assertEquals(ProductResultCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("브랜드 ID로 카테고리 포함한 상품 목록 조회")
    @Transactional
    void testFindProductsByBrandIdWithCategory() {
        List<Product> products = productDataService.findProductsByBrandIdWithCategory(1L);

        assertFalse(products.isEmpty());
        assertEquals(1L, products.get(0).getBrand().getId());
    }

    @Test
    @DisplayName("브랜드에 속한 모든 상품 삭제 테스트")
    void testDeleteProductsByBrand() {
        productDataService.deleteProductsByBrand(1L);

        List<Product> productsAfterDelete = productRepository.findAll();
        assertTrue(productsAfterDelete.stream().noneMatch(p -> p.getBrand().getId() == 1L));
    }

    @Test
    @DisplayName("카테고리별 최저가 상품 조회 테스트")
    void testFindLowestPricePerCategory() {
        List<Product> lowestPriceProducts = productDataService.findLowestPricePerCategory();

        assertEquals(3L, lowestPriceProducts.get(0).getBrand().getId());
        assertEquals("C", lowestPriceProducts.get(0).getBrand().getName());
        assertEquals(10000l, lowestPriceProducts.get(0).getPrice());
    }

    @Test
    @DisplayName("브랜드의 최저 총액 조회")
    void testFindBrandWithLowestTotalPrice() {
        BrandPriceData brandData = productDataService.findBrandWithLowestTotalPrice();

        assertNotNull(brandData);
        assertEquals("D", brandData.getBrandName());
    }
}
