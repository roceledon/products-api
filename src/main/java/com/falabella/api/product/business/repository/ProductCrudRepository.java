package com.falabella.api.product.business.repository;

import com.falabella.api.product.common.domain.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductCrudRepository extends ReactiveCrudRepository<Product, String> {
    @Query(value = "{'sku' : ?0 }")
    Mono<Product> findBySku(String sku);
}
