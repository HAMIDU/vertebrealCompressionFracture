package com.karizma.onlineshopping.purchase.mapper;

import com.karizma.onlineshopping.base.GenericMapper;
import com.karizma.onlineshopping.product.dto.ProductConfigDto;
import com.karizma.onlineshopping.product.entity.ProductConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductConfigMapper extends GenericMapper<ProductConfig, ProductConfigDto> {
    ProductConfigMapper INSTANCE = Mappers.getMapper(ProductConfigMapper.class);

    @Mapping(source = "product.id", target = "productId")
    @Override
    ProductConfigDto toDTO(ProductConfig productConfig);

    @Mapping(source = "productId", target = "product.id")
    @Override
    ProductConfig toEntity(ProductConfigDto productConfigDto);

    @Override
    List<ProductConfigDto> toDtoList(List<ProductConfig> list);

    @Override
    List<ProductConfig> toEntityList(List<ProductConfigDto> list);

}
