package com.example.bebenshop.services;

import com.example.bebenshop.dto.produces.ProductImageProduceDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {

    ProductImageProduceDto addProductImage(Long id, MultipartFile multipartFile) throws IOException;

    ProductImageProduceDto editProductImage(Long id, MultipartFile multipartFile) throws IOException;

    void deleteProductImage(Long id);
}
