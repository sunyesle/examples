package com.sunyesle.spring_boot_jpa.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestBody ProductRequest dto) {
        productService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
