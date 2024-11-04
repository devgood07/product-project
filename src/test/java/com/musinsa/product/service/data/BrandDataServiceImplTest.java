package com.musinsa.product.service.data;

import com.musinsa.product.entity.Brand;
import com.musinsa.product.repository.BrandRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BrandDataServiceImplTest {

    @Autowired
    private BrandDataServiceImpl brandDataService;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("기존 브랜드가 존재하면 조회 - findByNameOrElseSave")
    void findByNameOrElseSave_ExistingBrand() {
        // Given
        String existingBrandName = "A"; // data.sql에 있는 브랜드 이름

        // When
        Brand brand = brandDataService.findByNameOrElseSave(existingBrandName);

        // Then
        assertEquals(existingBrandName, brand.getName());
    }

    @Test
    @DisplayName("브랜드가 없으면 새로 저장 - findByNameOrElseSave")
    void findByNameOrElseSave_NewBrand() {
        // Given
        String newBrandName = "NewBrand";

        // When
        Brand brand = brandDataService.findByNameOrElseSave(newBrandName);

        // Then
        assertNotNull(brand.getId());
        assertEquals(newBrandName, brand.getName());

        // DB에 브랜드가 저장되었는지 확인
        Optional<Brand> savedBrand = brandRepository.findByName(newBrandName);
        assertTrue(savedBrand.isPresent());
        assertEquals(newBrandName, savedBrand.get().getName());
    }

    @Test
    @DisplayName("브랜드 ID로 삭제 - deleteBrand")
    void deleteBrand() {
        // Given
        long brandIdToDelete = 1l;

        // When
        brandDataService.deleteBrand(brandIdToDelete);

        // Then
        assertFalse(brandRepository.findById(brandIdToDelete).isPresent());
    }

    @Test
    @DisplayName("브랜드 이름으로 ID 조회 - findBrandIdByName")
    void findBrandIdByName_ExistingBrand() {
        // Given
        String existingBrandName = "A"; // data.sql에 있는 브랜드 이름

        // When
        Optional<Long> brandId = brandDataService.findBrandIdByName(existingBrandName);

        // Then
        assertTrue(brandId.isPresent());
    }

    @Test
    @DisplayName("존재하지 않는 브랜드 이름으로 ID 조회 - findBrandIdByName")
    void findBrandIdByName_NonExistingBrand() {
        // Given
        String nonExistingBrandName = "NonExistingBrand";

        // When
        Optional<Long> brandId = brandDataService.findBrandIdByName(nonExistingBrandName);

        // Then
        assertFalse(brandId.isPresent());
    }

    @Test
    @DisplayName("브랜드 ID로 브랜드 조회 - findBrandById")
    void findBrandById_ExistingBrand() {
        // Given
        Brand existingBrand = brandRepository.findByName("A").orElseThrow();
        Long brandId = existingBrand.getId();

        // When
        Optional<Brand> foundBrandId = brandDataService.findBrandById(brandId);

        // Then
        assertTrue(foundBrandId.isPresent());
        assertEquals(brandId, foundBrandId.get().getId());
    }

    @Test
    @DisplayName("브랜드 업데이트 - updateBrand")
    void updateBrand() {
        // Given
        Brand brand = brandRepository.findByName("A").orElseThrow();
        brand.setName("UpdatedBrand");

        // When
        brandDataService.updateBrand(brand);

        // Then
        Brand updatedBrand = brandRepository.findById(brand.getId()).orElseThrow();
        assertEquals("UpdatedBrand", updatedBrand.getName());
    }
}

