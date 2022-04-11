package com.falabella.api.product.business.service;

import com.falabella.api.product.business.repository.ProductCrudRepository;
import com.falabella.api.product.business.service.ProductService;
import com.falabella.api.product.common.domain.Product;
import com.falabella.api.product.common.exception.BadRequestException;
import com.falabella.api.product.common.exception.NotFoundException;
import com.falabella.api.product.common.util.ValidateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class ProductServiceTest {
    private static ProductService productService;

    @BeforeAll
    public static void init() {
        ProductCrudRepository productCrudRepository = new ProductCrudRepository() {
            @Override
            public Mono<Product> findBySku(String sku) {
                return (sku.equals("FAL-1000000")) ? Mono.just(new Product("1")) : Mono.empty();
            }

            @Override
            public <S extends Product> Mono<S> save(S entity) {
                return (ValidateUtil.validateProduct(entity)) ? Mono.just(entity) : Mono.empty();
            }

            @Override
            public <S extends Product> Flux<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public <S extends Product> Flux<S> saveAll(Publisher<S> entityStream) {
                return null;
            }

            @Override
            public Mono<Product> findById(String s) {
                return null;
            }

            @Override
            public Mono<Product> findById(Publisher<String> id) {
                return null;
            }

            @Override
            public Mono<Boolean> existsById(String s) {
                return null;
            }

            @Override
            public Mono<Boolean> existsById(Publisher<String> id) {
                return null;
            }

            @Override
            public Flux<Product> findAll() {
                return Flux.just(new Product());
            }

            @Override
            public Flux<Product> findAllById(Iterable<String> strings) {
                return null;
            }

            @Override
            public Flux<Product> findAllById(Publisher<String> idStream) {
                return null;
            }

            @Override
            public Mono<Long> count() {
                return null;
            }

            @Override
            public Mono<Void> deleteById(String s) {
                return Mono.empty();
            }

            @Override
            public Mono<Void> deleteById(Publisher<String> id) {
                return null;
            }

            @Override
            public Mono<Void> delete(Product entity) {
                return Mono.empty();
            }

            @Override
            public Mono<Void> deleteAllById(Iterable<? extends String> strings) {
                return null;
            }

            @Override
            public Mono<Void> deleteAll(Iterable<? extends Product> entities) {
                return null;
            }

            @Override
            public Mono<Void> deleteAll(Publisher<? extends Product> entityStream) {
                return null;
            }

            @Override
            public Mono<Void> deleteAll() {
                return null;
            }
        };
        productService = new ProductService(productCrudRepository);
    }

    @Test
    public void testGetAllProductWorks() {
        ProductService ps = Mockito.mock(productService.getClass());
        Mockito.when(ps.getAllProducts()).thenReturn(Flux.just(new Product()));
        productService.getAllProducts();
    }

    @Test
    public void testGetProductBySkuWorks() throws BadRequestException {
        var sku = "FAL-1000000";
        Product product = new Product();
        product.setSku(sku);
        ProductService ps = Mockito.mock(productService.getClass());
        Mockito.when(ps.getProductBySku(sku)).thenReturn(Mono.just(product));
        productService.getProductBySku(sku).block();
        Assertions.assertEquals(sku, Objects.requireNonNull(ps.getProductBySku("FAL-1000000").block()).getSku());
    }

    @Test
    public void testGetProductBySkuFails() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            productService.getProductBySku(null);
        });
        Assertions.assertThrows(BadRequestException.class, () -> {
            productService.getProductBySku("");
        });
    }

    @Test
    public void testDeleteProductBySkuWorks() throws BadRequestException {
        var sku = "FAL-1000000";
        Product product = new Product();
        product.setSku(sku);
        ProductService ps = Mockito.mock(productService.getClass());
        Mockito.when(ps.deleteProductBySku(sku)).thenReturn(Mono.just(product));
        productService.deleteProductBySku(sku).block();
        Assertions.assertEquals(sku, Objects.requireNonNull(ps.deleteProductBySku(sku).block()).getSku());
    }

    @Test
    public void testGetDeleteBySkuFails() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            productService.deleteProductBySku(null);
        });
        Assertions.assertThrows(BadRequestException.class, () -> {
            productService.deleteProductBySku("");
        });
    }

    @Test
    public void testInsertProductBySkuWorks() throws BadRequestException, MalformedURLException {
        Product product = new Product();
        product.setId("1");
        product.setSku("FAL-1000000");
        product.setName("product");
        product.setBrand("brand");
        product.setSize("size");
        product.setPrice(100D);
        product.setPrincipalImage(new URL("https://images.falabella.com/v3/assets/blt7c5c2f2f888a7cc3/blta97d44edaed86fbc/61dc4cfb1757dc6aed2adcea/FALABELLA-100122.jpg"));
        product.setOtherImages(List.of(new URL[]{new URL("https://images.falabella.com/v3/assets/blt7c5c2f2f888a7cc3/blta97d44edaed86fbc/61dc4cfb1757dc6aed2adcea/FALABELLA-100122.jpg")}));
        productService.saveProduct(product).block();
        ProductService ps = Mockito.mock(productService.getClass());
        Mockito.when(ps.saveProduct(product)).thenReturn(Mono.just(product));
        Assertions.assertEquals(product, Objects.requireNonNull(ps.saveProduct(product).block()));
    }

    @Test
    public void testInsertProductBySkuFails() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            productService.saveProduct(null);
        });
    }

    @Test
    public void testUpdateProductBySkuWorks() throws BadRequestException, MalformedURLException {
        Product product = new Product();
        product.setSku("FAL-1000000");
        product.setName("product");
        product.setBrand("brand");
        product.setSize("size");
        product.setPrice(100D);
        product.setPrincipalImage(new URL("https://images.falabella.com/v3/assets/blt7c5c2f2f888a7cc3/blta97d44edaed86fbc/61dc4cfb1757dc6aed2adcea/FALABELLA-100122.jpg"));
        product.setOtherImages(List.of(new URL[]{new URL("https://images.falabella.com/v3/assets/blt7c5c2f2f888a7cc3/blta97d44edaed86fbc/61dc4cfb1757dc6aed2adcea/FALABELLA-100122.jpg")}));
        productService.updateProduct(product).block();
        ProductService ps = Mockito.mock(productService.getClass());
        Mockito.when(ps.updateProduct(product)).thenReturn(Mono.just(product));
        Assertions.assertEquals(product, Objects.requireNonNull(ps.updateProduct(product).block()));
    }


    @Test
    public void testUpdateProductBySkuFails() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            productService.updateProduct(null);
        });
    }
}
