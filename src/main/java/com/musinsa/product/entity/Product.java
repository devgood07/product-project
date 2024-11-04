package com.musinsa.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_brand_price", columnList = "brand_id, price"),
        @Index(name = "idx_category_price", columnList = "category_id, price")}
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private long price;

    public Product(Brand brand, Category category, long price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }
}


