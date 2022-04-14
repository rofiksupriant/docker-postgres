package com.rofik.springpostgres.service;

import com.rofik.springpostgres.domain.dao.Category;
import com.rofik.springpostgres.domain.dao.Product;
import com.rofik.springpostgres.domain.dto.ProductDto;
import com.rofik.springpostgres.domain.dto.ProductListDto;
import com.rofik.springpostgres.repository.CategoryRepository;
import com.rofik.springpostgres.repository.ProductRepository;
import com.rofik.springpostgres.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rofik.springpostgres.constant.AppConstant.ResponseCode;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> addProduct(ProductDto request) {
        log.info("Executing add new product");
        try {
            Optional<Category> category = categoryRepository.findById(request.getCategory().getId());
            if (category.isEmpty()) {
                log.info("Category [{}] not found", request.getCategory().getId());
                return ResponseUtil.build(ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Product product = mapper.map(request, Product.class);
            product.setCategory(category.get());
            productRepository.save(product);
            
            return ResponseUtil.build(ResponseCode.SUCCESS, mapper.map(product, ProductDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Got an error when saving new product. Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllProduct(Long categoryId) {
        try {
            log.info("Executing get all product by category [{}]", categoryId);
            List<Product> products;
            List<ProductDto> productDtoList = new ArrayList<>();

            if (categoryId != null) products = productRepository.findAllByCategoryId(categoryId);
            else products = productRepository.findAll();

            for (Product product : products) {
                productDtoList.add(mapper.map(product, ProductDto.class));
            }

            return ResponseUtil.build(ResponseCode.SUCCESS, productDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Got an error when get all product by category. Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllProductPagination(ProductListDto request) {
        try {
            log.info("Executing get all product with pagination");
            int page = null == request.getPage() ? 0 : request.getPage();
            int size = null == request.getSize() ? 1 : request.getSize();
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> productPage = productRepository.findAll(pageable);

            log.info("Mapping page into dtos. Size: [{}]", productPage.getTotalElements());
            List<ProductDto> productDtoList = new ArrayList<>();
            
            for (Product product : productPage.getContent()) {
                productDtoList.add(mapper.map(product, ProductDto.class));
            }

            log.debug("List product dto: [{}]", productDtoList);

            ProductListDto productListDto = ProductListDto.builder()
                .products(productDtoList)
                .size(productPage.getSize())
                .page(productPage.getNumber())
                .totalPage(productPage.getTotalPages())
                .build();

                return ResponseUtil.build(ResponseCode.SUCCESS, productListDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Got an error when get all product with pagination. Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllProductSortByCategory(Sort.Direction direction) {
        try {
            log.info("Executing get all product sort by category [{}]", direction);
            List<Product> products = productRepository.findAll(Sort.by(direction, "category.id"));
            List<ProductDto> productDtoList = new ArrayList<>();
        
            for (Product product : products) {
                productDtoList.add(mapper.map(product, ProductDto.class));
            }

            return ResponseUtil.build(ResponseCode.SUCCESS, productDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Got an error when get all product sort by category. Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> searchProductByName(String productName) {
        try {
            log.info("Executing search product by name: [{}]", productName);
            Product product = productRepository.findByProductNameContaining(productName);

            return ResponseUtil.build(ResponseCode.SUCCESS, mapper.map(product, ProductDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Got an error when search product by product name. Error: {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}