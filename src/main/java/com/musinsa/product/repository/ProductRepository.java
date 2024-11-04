package com.musinsa.product.repository;


import com.musinsa.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    @Modifying
    @Query("DELETE FROM Product p WHERE p.brand.id = :brandId")
    void deleteProductsByBrand(@Param("brandId") Long brandId);
}