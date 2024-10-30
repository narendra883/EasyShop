//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.backend.services;

import com.backend.dto.ProductDTO;
import com.backend.model.Product;
import com.backend.repositories.ProductRepository;
import java.util.List;
import lombok.Generated;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productReository;

    public Product addProduct(ProductDTO productDTO) {
        Product product = this.toEntity(productDTO);
        return (Product)this.productReository.save(product);
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        return product;
    }

    public List<Product> getAllProducts() {
        return this.productReository.findAll();
    }

    @Generated
    public ProductService(final ProductRepository productReository) {
        this.productReository = productReository;
    }
}
