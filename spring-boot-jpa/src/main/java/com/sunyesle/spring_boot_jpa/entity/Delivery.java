package com.sunyesle.spring_boot_jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }
}
