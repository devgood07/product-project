package com.musinsa.product.repository;

import com.musinsa.product.config.QueryDSLConfig;
import com.musinsa.product.entity.Category;
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
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 이름으로 카테고리 찾기")
    void testCategoryFindByName() {
        // Given
        Category category = new Category();
        category.setName("TestCategory");
        categoryRepository.save(category);

        // When
        Optional<Category> foundCategory = categoryRepository.findByName("TestCategory");

        // Then
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getName()).isEqualTo("TestCategory");
    }

    @Test
    @DisplayName("존재 하지 않는 카테고리 이름으로 카테고리 찾기")
    void testFindByNonExistentName() {
        // When
        Optional<Category> foundCategory = categoryRepository.findByName("NonExistentCategory");

        // Then
        assertThat(foundCategory).isNotPresent();
    }
}
