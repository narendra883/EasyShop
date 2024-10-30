//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

import lombok.*;

@Entity
@Table(
        name = "products"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            length = 1000
    )
    private String description;
    @Column(
            nullable = false
    )
    private BigDecimal price;
    @Column(
            nullable = false
    )
    private Integer stockQuantity;

}