package com.musinsa.product.util;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductUtils {
    // 가격을 쉼표 포함 형식으로 변환
    static public String formatPrice(long price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(price);
    }
}
