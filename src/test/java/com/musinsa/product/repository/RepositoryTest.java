package com.musinsa.product.repository;

import com.musinsa.product.config.QueryDSLConfig;
import com.musinsa.product.dto.BrandPriceData;
import com.musinsa.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(QueryDSLConfig.class)
@Transactional
public class RepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("카테고리별 최저 가격 상품 찾기")
    void testFindLowestPricePerCategory() {
        List<Product> products = productRepository.findLowestPricePerCategory();
        assertThat(products).isNotEmpty().hasSize(9);
        assertThat(products).extracting("price").containsExactlyInAnyOrder(
                10000L, 5000L, 3000L, 9000L, 9000L, 2000L, 1500L, 1700L, 1900L);
    }

    @Test
    @DisplayName("브랜드별 최저 가격 상품 찾기")
    void testFindBrandWithLowestTotalPrice() {
        BrandPriceData brandPriceData = productRepository.findBrandWithLowestTotalPrice();
        assertThat(brandPriceData).isNotNull();
        assertThat(brandPriceData.getBrandName()).isIn("A", "D");
    }

    @Test
    @DisplayName("브랜드 id로 해당 브랜드의 카테고리별 상품 조회")
    void testFindProductsByBrandIdWithCategory() {
        Long brandId = 1L;
        List<Product> products = productRepository.findProductsByBrandIdWithCategory(brandId);
        assertThat(products).isNotEmpty();
        assertThat(products).allMatch(product -> Long.valueOf(product.getBrand().getId()).equals(brandId));
    }

    @Test
    @DisplayName("카테고리 이름으로 최저 가격 상품 찾기")
    void testFindLowestPriceByCategory() {
        String categoryName = "상의";
        List<Product> products = productRepository.findLowestPriceByCategory(categoryName);
        assertThat(products).isNotEmpty();
        assertThat(products.get(0).getPrice()).isEqualTo(10000L);
    }

    @Test
    @DisplayName("카테고리 이름으로 최고 가격 상품 찾기")
    void testFindHighestPriceByCategory() {
        String categoryName = "상의";
        List<Product> products = productRepository.findHighestPriceByCategory(categoryName);
        assertThat(products).isNotEmpty();
        assertThat(products.get(0).getPrice()).isEqualTo(11400L);
    }

}
