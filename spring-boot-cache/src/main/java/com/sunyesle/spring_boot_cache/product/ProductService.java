package com.sunyesle.spring_boot_cache.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "product-list", key = "#pageable.pageNumber", condition = "#pageable.pageNumber <= 5")
    public Page<ProductResponse> getProducts(Pageable pageable) {
        log.info("getProducts 호출");
        return productRepository.findAll(pageable).map(ProductResponse::of);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProduct(Long id) {
        log.info("getProduct 호출");
        return productRepository.findById(id).map(ProductResponse::of)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @CachePut(value = "products", key = "#result.id")
    public ProductResponse saveProduct(ProductRequest request) {
        log.info("saveProduct 호출");
        Product product = request.toEntity();
        productRepository.save(product);
        return ProductResponse.of(product);
    }

    @Transactional
    @CachePut(value = "products", key = "#id")
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("updateProduct 실행");
        Product product = productRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        product.changeName(request.getName());
        product.changePrice(request.getPrice());

        return ProductResponse.of(product);
    }

    @Transactional
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        log.info("deleteProduct 실행");
        productRepository.deleteById(id);
    }
}
