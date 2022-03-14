package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;

public interface ProductService {

    ProductProduceDto createProduct(ProductConsumeDto productConsumeDto);
}
