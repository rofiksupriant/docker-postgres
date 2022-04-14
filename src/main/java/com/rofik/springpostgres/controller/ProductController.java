package com.rofik.springpostgres.controller;

import com.rofik.springpostgres.domain.dto.ProductDto;
import com.rofik.springpostgres.domain.dto.ProductListDto;
import com.rofik.springpostgres.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewProduct(@RequestBody ProductDto request) {
        return productService.addProduct(request);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllProduct(@RequestParam(value = "category_id", required = false) Long categoryId) {
        return productService.getAllProduct(categoryId);
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<Object> getAllProductPagination(@RequestBody ProductListDto request) {
        return productService.getAllProductPagination(request);
    }

    @GetMapping(value = "/sort-by-category")
    public ResponseEntity<Object> getAllProductSortByCategory(@RequestParam(value = "sort", required = true) Sort.Direction direction) {
        return productService.getAllProductSortByCategory(direction);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchProduct(@RequestParam(value = "product_name") String productName) {
        return productService.searchProductByName(productName);
    }
    
    
}