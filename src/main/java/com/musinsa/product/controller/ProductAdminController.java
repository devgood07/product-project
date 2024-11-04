package com.musinsa.product.controller;

import com.musinsa.product.dto.ModifyProductByActionRequest;
import com.musinsa.product.dto.ModifyProductByActionResponse;
import com.musinsa.product.service.ProductAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name = "관리자 브랜드 및 상품 수정 API", description = "브랜드 및 상품을 추가 / 업데이트 / 삭제 API")
@RequestMapping("/admin/api/v1/products")
public class ProductAdminController {
    private final ProductAdminService administratorProductService;

    @Operation(summary = "브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API"
            , description = "action 및 brandId, productId에 따라 브랜드 및 상품을 추가 / 업데이트 / 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "액션 성공", content = @Content(schema = @Schema(implementation = ModifyProductByActionResponse.class)))
    @PostMapping("/modify-product-by-action")
    public ResponseEntity<?> modifyProductByAction(@Validated @RequestBody ModifyProductByActionRequest request) {
        ModifyProductByActionResponse response = administratorProductService.modifyProductByAction(request);
        return ResponseEntity.ok(response);
    }
}
