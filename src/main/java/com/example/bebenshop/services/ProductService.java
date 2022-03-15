package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.dto.produces.CategoryProduce1Dto;
import com.example.bebenshop.dto.produces.ProductProduceDto;

import java.util.HashMap;
import java.util.List;


public interface ProductService {

    ProductProduceDto createProduct(ProductConsumeDto productConsumeDto);

    ProductProduceDto editProduct(Long id, HashMap<String, Object> map);

    void deleteProductByID(Long id);

    List<ProductProduceDto> getAll(Boolean structure);
}
