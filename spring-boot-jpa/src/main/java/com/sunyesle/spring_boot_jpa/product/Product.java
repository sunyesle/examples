package com.sunyesle.spring_boot_jpa.product;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Convert(converter=BooleanToYNConverter.class)
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
