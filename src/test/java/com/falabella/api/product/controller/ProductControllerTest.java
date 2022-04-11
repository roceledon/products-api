package com.falabella.api.product.controller;

import com.falabella.api.product.business.service.ProductService;
import com.falabella.api.product.common.domain.Product;
import com.falabella.api.product.common.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductControllerTest {

    private final ProductService productService = Mockito.mock(ProductService.class);
    private final ProductController productController = new ProductController(productService);

    @Test
    public void shouldGetBySkuWorks() throws BadRequestException {
        Product product = new Product();
        product.setSku("FAL-1000015");

        Mockito.when(productService.getProductBySku(Mockito.anyString())).thenReturn(Mono.just(product));

        productController.getProductBySku("FAL-1000015").block();
        Mockito.verify(productService, Mockito.times(1)).getProductBySku("FAL-1000015");
    }

    @Test
    public void shouldGetBySkuFail() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Mockito.when(productService.getProductBySku(Mockito.anyString())).thenThrow(new RuntimeException());

            productController.getProductBySku("TAL-1000015").block();
            Mockito.verify(productService, Mockito.times(1)).getProductBySku("TAL-1000015");
        });
    }

    @Test
    public void shouldGetAllWorks() {
        Product product = new Product();
        product.setSku("FAL-1000000");

        Mockito.when(productService.getAllProducts()).thenReturn(Flux.just(product));

        productController.getAllProducts().blockLast();
        Mockito.verify(productService, Mockito.times(1)).getAllProducts();
    }

    @Test
    public void shouldGetAllFails() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Mockito.when(productService.getAllProducts()).thenThrow(new RuntimeException());

            productController.getAllProducts().blockLast();
            Mockito.verify(productService, Mockito.times(1)).getAllProducts();
        });
    }

    @Test
    public void shouldInsertWorks() throws BadRequestException, MalformedURLException {
        Product product = new Product();
        product.setSku("FAL-1000000");
        product.setName("product");
        product.setBrand("brand");
        product.setPrice(100D);
        product.setPrincipalImage(new URL("https://images.falabella.com/v3/assets/blt7c5c2f2f888a7cc3/blta97d44edaed86fbc/61dc4cfb1757dc6aed2adcea/FALABELLA-100122.jpg"));

        Mockito.when(productService.saveProduct(product)).thenReturn(Mono.just(product));

        productController.insertProduct(product);
        Mockito.verify(productService, Mockito.times(1)).saveProduct(product);
    }

    @Test
    public void shouldInsertFail() throws RuntimeException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Product product = new Product();
            product.setSku("FAL-1000000");

            Mockito.doThrow(new RuntimeException()).when(productService).saveProduct(product);

            productController.insertProduct(product);
        });
    }

    @Test
    public void shouldUpdateWorks() throws BadRequestException, MalformedURLException {
        Product product = new Product();
        product.setSku("FAL-1000000");
        product.setName("product_test");
        product.setBrand("brand");
        product.setPrice(100D);
        product.setPrincipalImage(new URL("https://es.wikipedia.org/wiki/Falabella#/media/Archivo:Fallabela_inicios_siglo_XX.jpg"));

        Mockito.when(productService.updateProduct(product)).thenReturn(Mono.just(product));

        productController.updateProduct(product);
        Mockito.verify(productService, Mockito.times(1)).updateProduct(product);
    }

    @Test
    public void shouldUpdateFail() throws RuntimeException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Product product = new Product();
            product.setSku("FAL-1000000");

            Mockito.doThrow(new RuntimeException()).when(productService).updateProduct(product);

            productController.updateProduct(product);
        });
    }

    @Test
    public void shouldDeleteByIdWorks() throws BadRequestException {
        Mockito.when(productService.deleteProductBySku(Mockito.anyString())).thenReturn(Mono.empty());
        productController.deleteProductBySku(Mockito.anyString());
        Mockito.verify(productService, Mockito.times(1)).deleteProductBySku(Mockito.anyString());
    }

    @Test
    public void shouldDeleteByIdFail() throws RuntimeException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Mockito.doThrow(new RuntimeException()).when(productService).deleteProductBySku(Mockito.anyString());
            productController.deleteProductBySku(Mockito.anyString());
        });
    }
}
