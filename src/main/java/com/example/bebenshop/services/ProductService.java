package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.dto.produces.CategoryProduce1Dto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductProduceDto> getAll(Boolean structure);

    ProductProduceDto createProduct(ProductConsumeDto productConsumeDto);
}
