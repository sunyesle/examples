package com.sunyesle.spring_boot_jpa.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void save(ProductRequest dto) {
        Product product = new Product(dto.getName(), dto.isEnabled(), dto.getProductType());
        productRepository.save(product);
    }
}
