package com.musinsa.product.validation;

import com.musinsa.product.dto.ModifyProductByActionRequest;
import com.musinsa.product.validation.annotation.ValidAction;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class ActionValidator implements ConstraintValidator<ValidAction, ModifyProductByActionRequest> {

    @Override
    public boolean isValid(ModifyProductByActionRequest request, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(request.getAction())) {
            return false;
        }

        switch (request.getAction().toLowerCase()) {
            case "add_brand":
                return validateAddBrandAction(request);
            case "update_brand":
                return validateUpdateBrandAction(request);
            case "delete_brand":
                return validateDeleteBrandAction(request);
            case "add_product":
                return validateAddProductAction(request);
            case "update_product":
                return validateUpdateAction(request);
            case "delete_product":
                return validateDeleteAction(request);
            default:
                return false;
        }
    }

    private boolean validateDeleteBrandAction(ModifyProductByActionRequest request) {
        if (request.getBrandId() == null){
            return false;
        }

        if (request.getBrandId() <= 0) {
            return false;
        }

        return true;
    }

    private boolean validateUpdateBrandAction(ModifyProductByActionRequest request) {
        if (request.getBrandId() == null || StringUtils.isEmpty(request.getBrandName())) {
            return false;
        }

        if (request.getBrandId() <= 0) {
            return false;
        }

        return true;
    }

    private boolean validateAddBrandAction(ModifyProductByActionRequest request) {
        if (StringUtils.isEmpty(request.getBrandName())) {
            return false;
        }

        return true;
    }

    private boolean validateAddProductAction(ModifyProductByActionRequest request) {
        if (StringUtils.isEmpty(request.getBrandName())) {
            return false;
        }

        if (StringUtils.isEmpty(request.getCategoryName())) {
            return false;
        }

        if (StringUtils.isEmpty(request.getPrice())) {
            return false;
        }

        if(!StringUtils.isNumeric(request.getPrice()) || Long.parseLong(request.getPrice()) <= 0) {
            return false;
        }

        return true;
    }

    private boolean validateUpdateAction(ModifyProductByActionRequest request) {
        if (request.getProductId() == null) {
            return false;
        }

        if (request.getProductId() <= 0) {
            return false;
        }

        if(StringUtils.isNotEmpty(request.getPrice())) {
            if(!StringUtils.isNumeric(request.getPrice()) || Long.parseLong(request.getPrice()) <= 0) {
                return false;
            }
        }

        // categoryName, brandName, price는 선택적으로 허용됨
        return true;
    }

    private boolean validateDeleteAction(ModifyProductByActionRequest request) {
        // productId와 brandName 중 하나는 필수
        if (request.getProductId() == null && request.getBrandId() == null) {
            return false;
        }

        if(request.getProductId() != null && request.getProductId() <= 0) {
            return false;
        }

        if(request.getBrandId() != null && request.getBrandId() <= 0) {
            return false;
        }

        return true;
    }
}


