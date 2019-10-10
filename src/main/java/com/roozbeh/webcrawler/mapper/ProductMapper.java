package com.roozbeh.webcrawler.mapper;

import com.roozbeh.webcrawler.dal.entity.Product;
import com.roozbeh.webcrawler.model.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * @author rezaeian
 */
@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper {

    Product productModelToProduct(ProductModel source);
}
