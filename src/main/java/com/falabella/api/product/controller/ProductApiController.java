package com.falabella.api.product.controller;

import com.falabella.api.product.common.domain.Product;
import com.falabella.api.product.business.service.ProductService;
import com.falabella.api.product.common.exception.BadRequestException;
import com.falabella.api.product.common.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Mono<Product> getProductBySku(@PathVariable String sku) throws BadRequestException {
        return productService.getProductBySku(sku);
    }

    @PostMapping()
    private Mono<ResponseEntity<Product>> insertProduct(@RequestBody Product product) throws BadRequestException {
        return productService.saveProduct(product).map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p));
    }

    @PutMapping()
    private Mono<ResponseEntity<Product>> updateProduct(@RequestBody Product product) throws BadRequestException {
        return productService.updateProduct(product).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{sku}")
    private Mono<ResponseEntity<Void>> deleteProductBySku(@PathVariable String sku) throws BadRequestException {
       return productService.deleteProductBySku(sku).map( r -> ResponseEntity.ok().<Void>build());
    }
}
