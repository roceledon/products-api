package com.falabella.api.product.common.util;

import com.falabella.api.product.common.domain.Product;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ValidateUtilTest {
    @Test
    public void testProductIsValid() throws MalformedURLException {
        ValidateUtil validateUtil = new ValidateUtil();
        Product product = new Product();
        product.setSku("FAL-1000000");
        product.setName("product");
        product.setBrand("brand");
        product.setSize("size");
        product.setPrice(100D);
        product.setPrincipalImage(new URL("https://images.falabella.com/v3/assets/blt7c5c2f2f888a7cc3/blta97d44edaed86fbc/61dc4cfb1757dc6aed2adcea/FALABELLA-100122.jpg"));
        product.setOtherImages(List.of(new URL[]{new URL("https://images.falabella.com/v3/assets/blt7c5c2f2f888a7cc3/blta97d44edaed86fbc/61dc4cfb1757dc6aed2adcea/FALABELLA-100122.jpg")}));
        assert ValidateUtil.validateProduct(product);
    }

    @Test
    public void testProductIsNotValid() {
        Product product = new Product();
        assert !ValidateUtil.validateProduct(product);
        product.setSku("FAL-1000000");
        assert !ValidateUtil.validateProduct(product);
        product.setPrice(100D);
        assert !ValidateUtil.validateProduct(product);
        product.setName("product");
        assert !ValidateUtil.validateProduct(product);
        product.setBrand("brand");
        assert !ValidateUtil.validateProduct(product);
        assert !ValidateUtil.validateProduct(null);
    }

    @Test
    public void testSkuIsValid() {
        assert ValidateUtil.validateSku("FAL-1000000");
    }

    @Test
    public void testSkuIsNotValid() {
        assert !ValidateUtil.validateSku(null);
        assert !ValidateUtil.validateSku("");
        assert !ValidateUtil.validateSku("FAR-0");
        assert !ValidateUtil.validateSku("FAL-");
        assert !ValidateUtil.validateSku("FAL-100000000");
        assert !ValidateUtil.validateSku("FAL-0");
    }

    @Test
    public void testPriceIsValid() {
        assert ValidateUtil.validatePrice(1D);
    }

    @Test
    public void testPriceIsNotValid() {
        assert !ValidateUtil.validatePrice(null);
        assert !ValidateUtil.validatePrice(0D);
        assert !ValidateUtil.validatePrice(100000000D);
    }

    @Test
    public void testNameIsValid() {
        assert ValidateUtil.validateName("product");
    }

    @Test
    public void testNameIsNotValid() {
        assert !ValidateUtil.validateName(null);
        assert !ValidateUtil.validateName("");
        assert !ValidateUtil.validateName("aa");
        assert !ValidateUtil.validateName("asdasdasdasdasd"
                +"asdasdasdasdasdasdasdasdasdasdascassfa");
    }

    @Test
    public void testBrandIsValid() {
        assert ValidateUtil.validateBrand("product");
    }

    @Test
    public void testBrandIsNotValid() {
        assert !ValidateUtil.validateBrand(null);
        assert !ValidateUtil.validateBrand("");
        assert !ValidateUtil.validateBrand("aa");
        assert !ValidateUtil.validateBrand("asdasdasdasdasd"
                +"asdasdasdasdasdasdasdasdasdasdascassfa");
    }

    @Test
    public void testPrincipalImageIsValid() throws MalformedURLException {
        assert ValidateUtil.validatePrincipalImage(new URL("https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png"));
    }

    @Test
    public void testPrincipalImageIsNotValid() {
        assert !ValidateUtil.validatePrincipalImage(null);
    }
}
