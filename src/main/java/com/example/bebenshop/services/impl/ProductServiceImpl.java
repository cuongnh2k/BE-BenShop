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
import com.example.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository mProductRepository;
    private final ProductMapper mProductMapper;
    private final CategoryRepository mCategoryRepository;
    private final ConvertUtil mConvertUtil;

    @Override
    public ProductProduceDto createProduct(ProductConsumeDto productConsumeDto) {
        ProductEntity productEntity = productConsumeDto.toProductEntity();
        List<CategoryEntity> categoryEntityList = mCategoryRepository.findAllById(mConvertUtil.toArray(productConsumeDto.getCategories()));
        
        productEntity.setCategories(categoryEntityList);
        mProductRepository.save(productEntity);

        return mProductMapper.toProductProduceDto(productEntity);

    }

    @Override
    public ProductProduceDto editProduct(Long id, HashMap<String, Object> map) {

        ProductEntity productEntity = mProductRepository.findById(id).orElse(null);
        if(productEntity == null){
            throw new BadRequestException("ID"+id+" does not exist");
        }
        List<ProductEntity> productEntityList = mProductRepository.findAll();
        for(String i: map.keySet()){
            switch (i){
                case "name":
                    String name= map.get(i).toString();
                    productEntity.setName(name);
                    break;
                case "price":
                    BigDecimal price = BigDecimal.valueOf(Long.parseLong(map.get(i).toString()));
                    productEntity.setPrice(price);
                    break;
                case "discount":
                    Integer discount = Integer.getInteger((String) map.get(i.toString()));
                    productEntity.setDiscount(discount);
                    break;
                case "status":
                    String status = map.get(i).toString();
                    productEntity.setStatus(status);
                    break;
                case "style":
                    String style = map.get(i).toString();
                    productEntity.setStyle(style);
                    break;
                case "gender":
                    String gender = map.get(i).toString();
                    productEntity.setGender(gender);
                    break;
                case "origin":
                    String origin = map.get(i).toString();
                    productEntity.setOrigin(origin);
                    break;
                case "material":
                    String material = map.get(i).toString();
                    productEntity.setMaterial(material);
                    break;
                case "productionMethod":
                    String productionMethod = map.get(i).toString();
                    productEntity.setProductionMethod(productionMethod);
                    break;
                case "size":
                    String size = map.get(i).toString();
                    productEntity.setSize(size);
                    break;
                case "accessory":
                    String accessory = map.get(i).toString();
                    productEntity.setAccessory(accessory);
                    break;
                case "washingMethod":
                    String washingMethod = map.get(i).toString();
                    productEntity.setWashingMethod(washingMethod);
                    break;
            }
        }

        return mProductMapper.toProductProduceDto(mProductRepository.save(productEntity));
    }


}
