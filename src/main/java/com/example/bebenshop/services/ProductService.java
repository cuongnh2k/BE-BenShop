package com.example.bebenshop.services;

import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.HashMap;


public interface ProductService {

    ProductProduceDto createProduct(ProductConsumeDto productConsumeDto);

    ProductProduceDto editProduct(Long id, HashMap<String, Object> map);

    void deleteProductByID(Long id);

    ProductProduceDto getProductById(Long id);

    BaseListProduceDto<ProductProduceDto> searchByTitleOrDescription(
            String search
            , Long categoryId
            , BigDecimal priceMin
            , BigDecimal priceMax
            , Pageable pageable);
}
