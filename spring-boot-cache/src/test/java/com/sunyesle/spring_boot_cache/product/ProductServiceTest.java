package com.sunyesle.spring_boot_cache.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    void getProductUsedCache() {
        // given
        Product product = new Product("apple", 2000);
        given(productRepository.findById(any()))
                .willReturn(Optional.of(product));

        // when
        IntStream.range(0, 10).forEach(i -> productService.getProduct(1L));

        // then
        verify(productRepository, times(1)).findById(1L);
    }
}