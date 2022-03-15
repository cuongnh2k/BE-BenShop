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
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (productEntity == null) {
            throw new BadRequestException("ID" + id + " does not exist");
        }
        List<ProductEntity> productEntityList = mProductRepository.findAll();
        for (String i : map.keySet()) {
            switch (i) {
                case "name":
                    productEntity.setName(map.get(i).toString());
                    break;
                case "price":
                    productEntity.setPrice(BigDecimal.valueOf(Long.parseLong(map.get(i).toString())));
                    break;
                case "discount":
                    productEntity.setDiscount(Integer.getInteger((String) map.get(i.toString())));
                    break;
                case "status":
                    productEntity.setStatus(map.get(i).toString());
                    break;
                case "style":
                    productEntity.setStyle(map.get(i).toString());
                    break;
                case "gender":
                    productEntity.setGender(map.get(i).toString());
                    break;
                case "origin":
                    productEntity.setOrigin(map.get(i).toString());
                    break;
                case "material":
                    productEntity.setMaterial(map.get(i).toString());
                    break;
                case "productionMethod":
                    productEntity.setProductionMethod(map.get(i).toString());
                    break;
                case "size":
                    productEntity.setSize(map.get(i).toString());
                    break;
                case "accessory":
                    productEntity.setAccessory(map.get(i).toString());
                    break;
                case "washingMethod":
                    productEntity.setWashingMethod(map.get(i).toString());
                    break;
            }
        }

        return mProductMapper.toProductProduceDto(mProductRepository.save(productEntity));
    }

    @Override
    public void deleteProductByID(Long id) {
        ProductEntity productEntity =mProductRepository.findById(id).orElse(null);
        if(Objects.isNull(productEntity)){
            throw new BadRequestException("Id"+id+"does not exist");
        }
        mProductRepository.deleteById(id);
    }

    @Override
    public List<ProductProduceDto> getAll(Boolean structure) {
        List<ProductProduceDto> productProduceDtoList = mProductRepository.findAll().stream()
                .map(mProductMapper::toProductProduceDto).collect(Collectors.toList());
        if(!structure){
            return productProduceDtoList.stream().map(
                    o -> ProductProduceDto.builder()
                    .id(o.getId())
                    .createdDate(o.getCreatedDate())
                    .updatedDate(o.getUpdatedDate())
                    .name(o.getName())
                    .price(o.getPrice())
                    .discount(o.getDiscount())
                    .status(o.getStatus())
                    .style(o.getStyle())
                    .gender(o.getGender())
                    .origin(o.getOrigin())
                    .material(o.getMaterial())
                    .productionMethod(o.getProductionMethod())
                    .size(o.getSize())
                    .accessory(o.getAccessory())
                    .washingMethod(o.getWashingMethod())
                    .build()).collect(Collectors.toList());
        }
        return productProduceDtoList.stream()
                .filter(o-> o.getId()==0)
                .map(o -> ProductProduceDto.builder()
                        .id(o.getId())
                        .createdDate(o.getCreatedDate())
                        .updatedDate(o.getUpdatedDate())
                        .name(o.getName())
                        .price(o.getPrice())
                        .discount(o.getDiscount())
                        .status(o.getStatus())
                        .style(o.getStyle())
                        .gender(o.getGender())
                        .origin(o.getOrigin())
                        .material(o.getMaterial())
                        .productionMethod(o.getProductionMethod())
                        .size(o.getSize())
                        .accessory(o.getAccessory())
                        .washingMethod(o.getWashingMethod())
                        .build()).collect(Collectors.toList()
                );
    }
}
