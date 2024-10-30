//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.backend.dto;

import jakarta.persistence.Column;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
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