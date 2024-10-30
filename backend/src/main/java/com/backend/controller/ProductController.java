//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.backend.controller;

import com.backend.dto.ProductDTO;
import com.backend.model.Product;
import com.backend.services.ProductService;
import java.util.List;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/products"})
@CrossOrigin(
        origins = {"http://localhost:5173"}
)
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProducts(@RequestBody ProductDTO productDTO) {
        Product product = this.productService.addProduct(productDTO);
        return new ResponseEntity(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllTodos() {
        return ResponseEntity.ok(this.productService.getAllProducts());
    }

    @Generated
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }
}
