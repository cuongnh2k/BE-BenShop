package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductProduceDto createProduct(ProductConsumeDto productConsumeDto);

    ProductProduceDto addProductImage(Long id, MultipartFile multipartFile) throws IOException;

    void deleteProductImage(Long id);
}
