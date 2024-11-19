package com.sunyesle.spring_boot_cache.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void getProduct_Cached() {
        // given
        Product product = new Product("apple", 2000);
        given(productRepository.findById(any()))
                .willReturn(Optional.of(product));

        // when
        IntStream.range(0, 10).forEach(i -> productService.getProduct(1L));

        // then
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProducts_PageNumberExceeds5_NotCached() {
        // given
        Pageable pageable = PageRequest.of(6, 10);
        Product product = new Product("apple", 2000);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);
        given(productRepository.findAll(any(Pageable.class)))
                .willReturn(productPage);

        // when
        productService.getProducts(pageable);
        productService.getProducts(pageable);

        // then
        verify(productRepository, times(2)).findAll(any(Pageable.class));
    }

    @Test
    void getProducts_PageNumberWithin5_Cached() {
        // given
        Pageable pageable = PageRequest.of(4, 10);
        Product product = new Product("apple", 2000);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);
        given(productRepository.findAll(any(Pageable.class)))
                .willReturn(productPage);

        // when
        productService.getProducts(pageable);
        productService.getProducts(pageable);

        // then
        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }
}