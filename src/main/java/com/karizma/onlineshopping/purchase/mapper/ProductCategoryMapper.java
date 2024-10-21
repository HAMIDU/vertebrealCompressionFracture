package com.karizma.onlineshopping.purchase.mapper;

import com.karizma.onlineshopping.base.GenericMapper;
import com.karizma.onlineshopping.product.dto.ProductCategoryDto;
import com.karizma.onlineshopping.product.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductCategoryMapper extends GenericMapper<ProductCategory, ProductCategoryDto> {
    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);


    @Override
    ProductCategoryDto toDTO(ProductCategory productCategory);


    @Override
    ProductCategory toEntity(ProductCategoryDto productCategoryDto);

    @Override
    List<ProductCategoryDto> toDtoList(List<ProductCategory> list);

    @Override
    List<ProductCategory> toEntityList(List<ProductCategoryDto> list);

}
