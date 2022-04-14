package com.rofik.springpostgres.service;

import com.rofik.springpostgres.constant.AppConstant;
import com.rofik.springpostgres.domain.dao.Category;
import com.rofik.springpostgres.domain.dto.CategoryDto;
import com.rofik.springpostgres.repository.CategoryRepository;
import com.rofik.springpostgres.util.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> addCategory(CategoryDto request) {
        log.info("Executing save new category");
        try {
            Category category = mapper.map(request, Category.class);
            categoryRepository.save(category);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, mapper.map(category, CategoryDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Got an error when saving new category. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}