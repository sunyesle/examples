package com.sunyesle.spring_boot_cache.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        log.info("getProduct 호출");
        return productRepository.findById(id).map(ProductResponse::of)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public ProductResponse saveProduct(ProductRequest request) {
        log.info("saveProduct 호출");
        Product product = request.toEntity();
        productRepository.save(product);
        return ProductResponse.of(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("updateProduct 실행");
        Product product = productRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        product.changeName(request.getName());
        product.changePrice(request.getPrice());

        return ProductResponse.of(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("deleteProduct 실행");
        productRepository.deleteById(id);
    }
}
