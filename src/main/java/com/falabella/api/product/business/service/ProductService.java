package com.falabella.api.product.business.service;

import com.falabella.api.product.common.domain.Product;
import com.falabella.api.product.business.repository.ProductCrudRepository;
import com.falabella.api.product.common.util.ValidateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductService {

    private final ProductCrudRepository productRepository;

    public ProductService(ProductCrudRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> findAllProducts() {
        return productRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    public Mono<Product> saveProduct(Product product) {
        if(ValidateUtil.validateProduct(product)) {
            return productRepository.findBySku(product.getSku())
                    .switchIfEmpty(productRepository.save(product))
                    .flatMap(p -> (p.getId().equals(product.getId())) ? Mono.just(p) : Mono.empty());
        } else {
            throw new IllegalArgumentException("Product is not valid");
        }
    }

    public Mono<Product> updateProduct(Product product) {
        if(ValidateUtil.validateProduct(product)) {
            return productRepository.findBySku(product.getSku())
                    .flatMap(p -> {
                        product.setId(p.getId());
                        return productRepository.save(product);
                    });
        } else {
            throw new IllegalArgumentException("Product is not valid");
        }
    }

    public Mono<Product> deleteProductBySku(String sku) {
        if (sku == null || sku.isEmpty()) {
            throw new IllegalArgumentException("Product cannot be null");
        } else {
            return productRepository.findBySku(sku)
                    .flatMap(p -> productRepository.delete(p)
                            .then(Mono.just(p)));
        }
    }
}
