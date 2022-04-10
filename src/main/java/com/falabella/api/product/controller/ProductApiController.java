package com.falabella.api.product.controller;

import com.falabella.api.product.common.domain.Product;
import com.falabella.api.product.business.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
public class ProductApiController {
    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    private Flux<Product> ListAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{sku}")
    private Mono<ResponseEntity<Product>> getProductBySku(@PathVariable String sku) {
        Mono<Product> product = productService.getProductBySku(sku);
        return product.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping()
    private Mono<ResponseEntity<Product>> insertProduct(@RequestBody Product product) {
        try {
            return productService.saveProduct(product)
                    .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p))
                    .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
        } catch (IllegalArgumentException e) {
            return Mono.just(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            return Mono.just(ResponseEntity.internalServerError().build());
        }
    }

    @PutMapping()
    private Mono<ResponseEntity<Product>> updateProduct(@RequestBody Product product) {
        try {
            return productService.updateProduct(product)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return Mono.just(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            return Mono.just(ResponseEntity.internalServerError().build());
        }
    }

    @DeleteMapping("/{sku}")
    private Mono<ResponseEntity<Void>> deleteProductBySku(@PathVariable String sku) {
        try {
           return productService.deleteProductBySku(sku).map( r -> ResponseEntity.ok().<Void>build())
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return Mono.just(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            return Mono.just(ResponseEntity.internalServerError().build());
        }
    }
}
