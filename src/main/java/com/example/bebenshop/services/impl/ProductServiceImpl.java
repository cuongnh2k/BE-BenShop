package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.CategoryEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.ProductMapper;
import com.example.bebenshop.repository.CategoryRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository mProductRepository;
    private final ProductMapper mProductMapper;
    private  final CategoryRepository mCategoryRepository;

    @Override
    public List<ProductProduceDto> getAll(Boolean structure) {
        List<ProductProduceDto> productProduceDtoList = mProductRepository.findAll().stream()
                .map(mProductMapper::toProductProduceDto).collect(Collectors.toList());

        return null;
    }

    @Override
    public ProductProduceDto createProduct(ProductConsumeDto productConsumeDto) {
        ProductEntity productEntity = productConsumeDto.toProductEntity();
        List<CategoryEntity> categoryEntityList = mCategoryRepository.findAll();
            if(productEntity.getName() != null || productEntity.getPrice()!= null || productEntity.getDescription() != null){
                throw  new BadRequestException("Khong duoc de trong");
            }else if(mProductRepository.existsAllByName(productEntity.getName())){
                throw  new BadRequestException("Ten"+ productEntity.getName()+"does not exits");
            }
            else {
                productEntity.setCategories(categoryEntityList);
                mProductRepository.save(productEntity);
                ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(productEntity);
                return productProduceDto;
            }
    }


}
