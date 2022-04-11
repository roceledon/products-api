package com.falabella.api.product.business.service;

import com.falabella.api.product.common.domain.Product;
import com.falabella.api.product.business.repository.ProductCrudRepository;
import com.falabella.api.product.common.exception.BadRequestException;
import com.falabella.api.product.common.exception.NotFoundException;
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

    public Flux<Product> getAllProducts() {
        return productRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Product> getProductBySku(String sku) throws BadRequestException {
        if(sku != null && !sku.isBlank()) {
            return productRepository.findBySku(sku)
                    .switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
        } else {
            throw new BadRequestException("SKU cannot be null");
        }
    }

    public Mono<Product> saveProduct(Product product) throws BadRequestException {
        if(ValidateUtil.validateProduct(product)) {
            return productRepository.findBySku(product.getSku())
                    .switchIfEmpty(productRepository.save(product))
                    .flatMap(p -> (p.getId().equals(product.getId())) ? Mono.just(p) : Mono.error(new BadRequestException("Product already exists")));
        } else {
            throw new BadRequestException("Product is not valid");
        }
    }

    public Mono<Product> updateProduct(Product product) throws BadRequestException {
        if(ValidateUtil.validateProduct(product)) {
            return productRepository.findBySku(product.getSku())
                    .switchIfEmpty(Mono.error(new NotFoundException("Product not found")))
                    .flatMap(p -> {
                        product.setId(p.getId());
                        return productRepository.save(product);
                    });
        } else {
            throw new BadRequestException( "Product is not valid");
        }
    }

    public Mono<Product> deleteProductBySku(String sku) throws BadRequestException {
        if (sku != null && !sku.isEmpty()) {
            return productRepository.findBySku(sku)
                    .switchIfEmpty(Mono.error(new NotFoundException("Product not found")))
                    .flatMap(p -> productRepository.delete(p)
                            .then(Mono.just(p)));
        } else {
            throw new BadRequestException("Product cannot be null");
        }
    }
}
