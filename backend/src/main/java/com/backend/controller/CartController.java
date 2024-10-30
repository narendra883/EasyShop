package com.backend.controller;

import com.backend.model.Cart;
import com.backend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartitems")
@CrossOrigin(origins = "https://narendraeasyshop.netlify.app")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    @PostMapping("/{user_id}/{product_id}")
    public ResponseEntity<String> addToCart(@PathVariable Long user_id, @PathVariable Long product_id, @RequestParam int quantity) {
        cartService.addToCart(user_id, product_id, quantity);
        return ResponseEntity.ok("Product added to cart successfully");
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<Cart>> getAllCartItems(@PathVariable Long user_id) {
        return ResponseEntity.ok(cartService.getAllCartItems(user_id));
    }
}
