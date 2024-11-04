package com.musinsa.product.controller;

import com.musinsa.product.dto.LowestPricePerBrandResponse;
import com.musinsa.product.dto.LowestPricePerCategoryResponse;
import com.musinsa.product.dto.PriceByCategoryResponse;
import com.musinsa.product.dto.ProductResultCode;
import com.musinsa.product.exception.ProductRuntimeException;
import com.musinsa.product.service.ProductSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@Tag(name = "카테고리/브랜드/상품 정보 조회 API", description = "카테고리/브랜드/상품 정보 조회 API")
@RequestMapping("/search/api/v1/products")
public class ProductSearchController {
    private final ProductSearchService ProductSearchService;

    @Operation(summary = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API",
            description = "카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있습니다. " +
                    "단, 카테고리별 최저가 브랜드가 복수일 경우, 가장 최근에 추가된 브랜드를 기준으로 조회 합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = LowestPricePerCategoryResponse.class)))
    @GetMapping("/lowest-price-per-category")
    public ResponseEntity<?> getLowestPricePerCategory() {
        LowestPricePerCategoryResponse response = ProductSearchService.getLowestPricePerCategory();
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API",
            description = "단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있습니다.")
    @GetMapping("/lowest-price-per-brand")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = LowestPricePerBrandResponse.class)))
    public ResponseEntity<?> getLowestPricePerBrand() {
        LowestPricePerBrandResponse response = ProductSearchService.getLowestPricePerBrand();
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API",
            description = "특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있습니다.")
    @Parameter(name = "category", description = "카테고리 이름")
    @GetMapping("/price-by-category/{category}")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PriceByCategoryResponse.class)))
    public ResponseEntity<?> getPriceByCategory(@PathVariable("category") String category) {
        if (category == null || category.isEmpty()) {
            throw new ProductRuntimeException(ProductResultCode.INVALID_INPUT_VALUE);
        }
        PriceByCategoryResponse response = ProductSearchService.getPriceByCategory(category);
        return ResponseEntity.ok(response);
    }
}
