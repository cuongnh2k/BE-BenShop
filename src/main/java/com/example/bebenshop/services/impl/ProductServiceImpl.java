package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.dto.produces.ProductCommentProduceDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.CategoryEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.entities.ProductImageEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.*;
import com.example.bebenshop.repository.CategoryRepository;
import com.example.bebenshop.repository.ProductImageRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.ProductService;
import com.example.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private final ProductCommentMapper mProductCommentMapper;
    private final UserMapper mUserMapper;
    private final ProductImageRepository mProductImageRepository;

    @Value("${root.directory}")
    String ROOT_DIRECTORY;

    @Value("${subfolder.product.image}")
    String SUBFOLDER_PRODUCT_IMAGE;

    @Value("${domain}")
    String DOMAIN;

    public ProductProduceDto toProductProduceDto(ProductEntity productEntity) {
        ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(productEntity);
        productProduceDto.setProductImages(productEntity.getProductImages().stream()
                .map(mProductImageMapper::toProductImageProduceDto).collect(Collectors.toList()));
        productProduceDto.setCategories(productEntity.getCategories().stream()
                .map(mCategoryMapper::toCategoryProduceDto).collect(Collectors.toList()));
        productProduceDto.setProductComments(productEntity.getProductComments().stream().map(o -> {
            ProductCommentProduceDto productCommentProduceDto = mProductCommentMapper.toProductCommentProduceDto(o);
            productCommentProduceDto.setUser(mUserMapper.toUserProduceDto(o.getUser()));
            return productCommentProduceDto;
        }).collect(Collectors.toList()));
        return productProduceDto;
    }

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
               case "categories":
                   List<CategoryEntity> categoryEntityList = mCategoryRepository.findAllById(mConvertUtil.toArray(map.get(i).toString()));
                   productEntity.setCategories(categoryEntityList);
            }
        }

        return mProductMapper.toProductProduceDto(mProductRepository.save(productEntity));
    }

    @Override
    public void deleteProductByID(Long id) {
        ProductEntity productEntity = mProductRepository.findById(id).orElse(null);
        if (Objects.isNull(productEntity)) {
            throw new BadRequestException("Id" + id + "does not exist");
        }
        productEntity.setDeletedFlag(true);
        mProductRepository.save(productEntity);
    }

    @Override
    public ProductProduceDto addProductImage(Long id, MultipartFile multipartFile) throws IOException {
        ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(id);
        if (productEntity == null) {
            throw new BadRequestException("no id exists: " + id);
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        {
            if (fileName.contains(".")) {
                String[] arr = multipartFile.getOriginalFilename().split("\\.");
                if (!arr[1].equalsIgnoreCase("JPG") && !arr[1].equalsIgnoreCase("PNG")) {
                    throw new BadRequestException("image must be in jpg or png format.");
                }
            } else {
                throw new BadRequestException("empty image");
            }
        }
        String uploadDir = ROOT_DIRECTORY + SUBFOLDER_PRODUCT_IMAGE + "/" + id;
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            if (mProductImageRepository.existsByPath(DOMAIN + SUBFOLDER_PRODUCT_IMAGE + "/" + id + "/" + multipartFile.getOriginalFilename())) {
                throw new BadRequestException("photo already exists");
            }
            mProductImageRepository.save(ProductImageEntity.builder()
                    .product(productEntity)
                    .path(DOMAIN + SUBFOLDER_PRODUCT_IMAGE + "/" + id + "/" + multipartFile.getOriginalFilename())
                    .build());

        } catch (Exception ioe) {
            throw new BadRequestException("empty image");
        }
        return toProductProduceDto(mProductRepository.findByIdAndDeletedFlagFalse(id));
    }

    @Override
    public void deleteProductImage(Long id) {
        ProductImageEntity productImageEntity = mProductImageRepository.findById(id).orElse(null);
        if (productImageEntity == null) {
            throw new BadRequestException("no id exists: " + id);
        }
        String[] arr = productImageEntity.getPath().split("/");
        String path = ROOT_DIRECTORY;
        for (int i = 3; i < arr.length; i++) {
            path += "/" + arr[i];
        }
        try {
            File file = new File(path);
            file.delete();
            mProductImageRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
