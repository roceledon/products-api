package com.falabella.api.product.common.util;

import com.falabella.api.product.common.domain.Product;

import java.net.URL;

public class ValidateUtil {
    public static boolean validateProduct(Product product) {
        return product != null
                && validateSku(product.getSku())
                && validatePrice(product.getPrice())
                && validateName(product.getName())
                && validateBrand(product.getBrand())
                && validatePrincipalImage(product.getPrincipalImage());
    }

    public static boolean validateSku(String sku) {
        if(sku != null && !sku.isBlank()) {
            String[] skus = sku.split("-");
            if(skus.length == 2
                    && "FAL".equals(skus[0])
                    && Integer.parseInt(skus[1]) >= 1000000
                    && Integer.parseInt(skus[1]) <= 9999999) {
                return true;
            }
        }
        return false;
    }

    public static boolean validatePrice(Double price) {
        return price != null
                && price >= 1D
                && price <= 99999999D;
    }

    public static boolean validateName(String name) {
        return name != null
                && !name.isBlank()
                && name.length() >= 3
                && name.length() <= 50;
    }

    public static boolean validateBrand(String brand) {
        return brand != null
                && !brand.isBlank()
                && brand.length() >= 3
                && brand.length() <= 50;
    }

    public static boolean validatePrincipalImage(URL principalImage) {
        return principalImage == null || !principalImage.toString().isBlank();
    }
}
