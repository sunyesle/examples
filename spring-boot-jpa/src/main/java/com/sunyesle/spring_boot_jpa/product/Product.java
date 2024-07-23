package com.sunyesle.spring_boot_jpa.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private boolean enabled;

    private ProductType productType;

    public Product() {
    }

    public Product(String name, boolean enabled, ProductType productType) {
        this.name = name;
        this.enabled = enabled;
        this.productType = productType;
    }
}
