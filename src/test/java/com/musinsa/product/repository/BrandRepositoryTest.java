package com.musinsa.product.repository;

import com.musinsa.product.config.QueryDSLConfig;
import com.musinsa.product.entity.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(QueryDSLConfig.class)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("브랜드 이름으로 브랜드 찾기")
    void testFindByName() {
        // Given
        Brand brand = new Brand("TestBrand");
        brandRepository.save(brand);

        // When
        Optional<Brand> foundBrand = brandRepository.findByName("TestBrand");

        // Then
        assertThat(foundBrand).isPresent();
        assertThat(foundBrand.get().getName()).isEqualTo("TestBrand");
    }

    @Test
    @DisplayName("브랜드 id로 브랜드 삭제하기")
    void testDeleteById() {
        // Given
        Brand brand = new Brand("TestBrandToDelete");
        brandRepository.save(brand);

        Optional<Brand> newBrand = brandRepository.findByName("TestBrandToDelete");
        assertThat(newBrand).isPresent();

        System.out.println("newBrand: " + newBrand.get().getName());

        // When
        brandRepository.deleteById(newBrand.get().getId());

        // Then
        Optional<Brand> deletedBrand = brandRepository.findByName("TestBrandToDelete");
        assertThat(deletedBrand).isNotPresent();

    }
}
