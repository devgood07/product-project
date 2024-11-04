package com.musinsa.product.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductUtilsTest {

    @Test
    @DisplayName("가격 형식 변환 테스트 - 천 단위 구분")
    void testFormatPriceWithThousands() {
        long price = 1234L;
        String formattedPrice = ProductUtils.formatPrice(price);
        assertEquals("1,234", formattedPrice);
    }

    @Test
    @DisplayName("가격 형식 변환 테스트 - 백만 단위 구분")
    void testFormatPriceWithMillions() {
        long price = 1234567L;
        String formattedPrice = ProductUtils.formatPrice(price);
        assertEquals("1,234,567", formattedPrice);
    }

    @Test
    @DisplayName("가격 형식 변환 테스트 - 억 단위 구분")
    void testFormatPriceWithLargeNumber() {
        long price = 123456789L;
        String formattedPrice = ProductUtils.formatPrice(price);
        assertEquals("123,456,789", formattedPrice);
    }

    @Test
    @DisplayName("가격 형식 변환 테스트 - 0원")
    void testFormatPriceWithZero() {
        long price = 0L;
        String formattedPrice = ProductUtils.formatPrice(price);
        assertEquals("0", formattedPrice);
    }
}
