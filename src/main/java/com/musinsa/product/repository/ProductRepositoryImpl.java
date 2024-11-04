package com.musinsa.product.repository;

import com.musinsa.product.dto.BrandPriceData;
import com.musinsa.product.entity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.musinsa.product.entity.Product;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<Product> findLowestPricePerCategory() {
        QProduct product = QProduct.product;
        QProduct subProduct = new QProduct("subProduct");

        return queryFactory
                .selectFrom(product)
                .join(product.brand, QBrand.brand).fetchJoin()
                .join(product.category, QCategory.category).fetchJoin()
                .where(product.price.eq(
                        JPAExpressions
                                .select(subProduct.price.min())
                                .from(subProduct)
                                .where(subProduct.category.eq(product.category))
                ))
                .orderBy(product.category.id.asc())
                .fetch();
    }


    @Override
    public BrandPriceData findBrandWithLowestTotalPrice() {
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(
                        BrandPriceData.class,
                        product.brand.id,
                        product.brand.name,
                        product.price.sum()
                ))
                .from(product)
                .join(product.brand, QBrand.brand)
                .groupBy(product.brand.id, product.brand.name)
                .having(product.category.countDistinct().eq(
                        JPAExpressions.select(category.count()).from(category)
                ))
                .orderBy(product.price.sum().asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<Product> findProductsByBrandIdWithCategory(Long brandId) {
        QProduct product = QProduct.product;

        return queryFactory
                .selectFrom(product)
                .where(product.brand.id.eq(brandId))
                .join(product.category, QCategory.category).fetchJoin()
                .fetch();
    }

    @Override
    public List<Product> findProductsByBrandId(Long brandId) {
        QProduct product = QProduct.product;
        return queryFactory.selectFrom(product)
                .where(product.brand.id.eq(brandId))
                .fetch();
    }

    @Override
    public List<Product> findLowestPriceByCategory(String categoryName) {
        QProduct product = QProduct.product;

        return queryFactory.selectFrom(product)
                .join(product.category, QCategory.category).fetchJoin()  // Category fetch join
                .join(product.brand, QBrand.brand).fetchJoin()  // Brand fetch join
                .where(product.category.name.eq(categoryName))
                .orderBy(product.price.asc(), product.id.asc())
                .limit(1)
                .fetch();
    }

    @Override
    public List<Product> findHighestPriceByCategory(String categoryName) {
        QProduct product = QProduct.product;

        return queryFactory.selectFrom(product)
                .join(product.category, QCategory.category).fetchJoin()
                .join(product.brand, QBrand.brand).fetchJoin()
                .where(product.category.name.eq(categoryName))
                .orderBy(product.price.desc(), product.id.asc())
                .limit(1)
                .fetch();
    }

    @Override
    public boolean existsByBrandId(Long brandId) {
        QProduct product = QProduct.product;
        return queryFactory.selectFrom(product)
                .where(product.brand.id.eq(brandId))
                .fetchFirst() != null;
    }

}
