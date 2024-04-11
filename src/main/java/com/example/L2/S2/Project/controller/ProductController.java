package com.example.L2.S2.Project.controller;

import com.example.L2.S2.Project.model.Product;
import com.example.L2.S2.Project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/allProduct")
    public List<Product> getAllProduct(){return productService.allProduct();}
}
