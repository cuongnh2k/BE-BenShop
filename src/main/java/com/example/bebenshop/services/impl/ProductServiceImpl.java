package com.example.bebenshop.services.impl;

import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.CategoryEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.CategoryMapper;
import com.example.bebenshop.mapper.ProductImageMapper;
import com.example.bebenshop.mapper.ProductMapper;
import com.example.bebenshop.repository.CategoryRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.ProductService;
import com.example.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ProductImageMapper mProductImageMapper;
    private final CategoryMapper mCategoryMapper;

    public ProductProduceDto toProductProduceDto(ProductEntity productEntity) {
        ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(productEntity);
        productProduceDto.setProductImages(productEntity.getProductImages().stream()
                .map(mProductImageMapper::toProductImageProduceDto).collect(Collectors.toList()));
        productProduceDto.setCategories(productEntity.getCategories().stream()
                .map(mCategoryMapper::toCategoryProduceDto).collect(Collectors.toList()));

        productProduceDto.setMoney(productProduceDto.getPrice()
                .divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(100 - productProduceDto.getDiscount())));
        return productProduceDto;
    }

    @Override
    public ProductProduceDto createProduct(ProductConsumeDto productConsumeDto) {
        ProductEntity productEntity = productConsumeDto.toProductEntity();
        List<CategoryEntity> categoryEntityList = mCategoryRepository
                .findAllById(mConvertUtil.toArray(productConsumeDto.getCategories()));
        productEntity.setCategories(categoryEntityList);
        mProductRepository.save(productEntity);
        ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(productEntity);
        productProduceDto.setCategories(productEntity.getCategories().stream().map(mCategoryMapper::toCategoryProduceDto).collect(Collectors.toList()));
        return productProduceDto;
    }

    @Override
    public ProductProduceDto editProduct(Long id, HashMap<String, Object> map) {
        ProductEntity productEntity = mProductRepository.findById(id).orElse(null);

        if (productEntity == null) {
            throw new BadRequestException("Product does not exist");
        }
        for (String i : map.keySet()) {
            switch (i) {
                case "name":
                    productEntity.setName(map.get(i).toString());
                    break;
                case "price":
                    productEntity.setPrice(BigDecimal.valueOf(Long.parseLong(map.get(i).toString())));
                    break;
                case "discount":
                    productEntity.setDiscount(Integer.parseInt(map.get(i).toString()));
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
                case "description":
                    productEntity.setDescription(map.get(i).toString());
                    break;
                case "categories":
                    productEntity.setCategories(mCategoryRepository.findAllById(mConvertUtil.toArray(map.get(i).toString())));
                    break;
            }
        }
        mProductRepository.save(productEntity);
        ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(productEntity);
        productProduceDto.setCategories(productEntity.getCategories().stream().map(mCategoryMapper::toCategoryProduceDto).collect(Collectors.toList()));
        return productProduceDto;
    }

    @Override
    public void deleteProductByID(Long id) {
        ProductEntity productEntity = mProductRepository.findById(id).orElse(null);
        if (Objects.isNull(productEntity)) {
            throw new BadRequestException("Product does not exist");
        }
        productEntity.setDeletedFlag(true);
        mProductRepository.save(productEntity);
    }

    @Override
    public ProductProduceDto getProductById(Long id) {
        ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(id);
        if (productEntity == null) {
            throw new BadRequestException("Product does not exist");
        }
        return toProductProduceDto(productEntity);
    }

    @Override
    public BaseListProduceDto<ProductProduceDto> searchByTitleOrDescription(
            String search
            , BigDecimal priceMin
            , BigDecimal priceMax
            , String categoryIds
            , Long productId
            , Pageable pageable) {

        Page<ProductEntity> productEntityPage = mProductRepository.searchByTitleOrDescription(
                search
                , priceMin
                , priceMax
                , categoryIds
                , !categoryIds.equals("-1") ? mProductRepository.getProductIdByCategoryId(mConvertUtil.toArray(categoryIds)) : null
                , productId
                , pageable);

        List<ProductProduceDto> productProduceDtoList = productEntityPage.getContent().stream().map(o -> {
            ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(o);
            productProduceDto.setMoney(productProduceDto.getPrice()
                    .divide(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(100 - productProduceDto.getDiscount())));
            productProduceDto.setProductImages(o.getProductImages().stream()
                    .map(mProductImageMapper::toProductImageProduceDto).collect(Collectors.toList()));
            productProduceDto.setCategories(o.getCategories().stream()
                    .map(mCategoryMapper::toCategoryProduceDto).collect(Collectors.toList()));
            return productProduceDto;
        }).collect(Collectors.toList());
        return BaseListProduceDto.<ProductProduceDto>builder()
                .content(productProduceDtoList)
                .totalElements(productEntityPage.getTotalElements())
                .totalPages(productEntityPage.getTotalPages())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }

    @Override
    public BaseListProduceDto<ProductProduceDto> searchProductByProductId(Long id, Pageable pageable) {
        Page<ProductEntity> productEntityPage = mProductRepository.searchProductByProductId(id, pageable);
        List<ProductEntity> productEntityList = productEntityPage.getContent();
        List<ProductProduceDto> productProduceDtoList = productEntityList.stream().map(this::toProductProduceDto).collect((Collectors.toList()));
        return BaseListProduceDto.<ProductProduceDto>builder()
                .content(productProduceDtoList)
                .totalElements(productEntityPage.getTotalElements())
                .totalPages(productEntityPage.getTotalPages())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }
}
