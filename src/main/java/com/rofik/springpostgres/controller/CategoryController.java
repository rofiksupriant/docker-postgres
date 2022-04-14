package com.rofik.springpostgres.controller;

import com.rofik.springpostgres.domain.dto.CategoryDto;
import com.rofik.springpostgres.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewCategory(@RequestBody CategoryDto request) {
        return categoryService.addCategory(request);
    }
    
}