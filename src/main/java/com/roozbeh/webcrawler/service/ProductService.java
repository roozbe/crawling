package com.roozbeh.webcrawler.service;

import com.roozbeh.webcrawler.dal.entity.Product;
import com.roozbeh.webcrawler.dal.repository.ProductRepository;
import com.roozbeh.webcrawler.mapper.ProductMapper;
import com.roozbeh.webcrawler.model.ProductModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author rezaeian
 */
@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public Product persist(ProductModel productModel) {
        return productRepository.findByName(productModel.getName())
                .orElse(productRepository.save(productMapper.productModelToProduct(productModel)));
    }

    void save(ProductModel productModel) {
        productRepository.save(productMapper.productModelToProduct(productModel));
    }

    public Product findProductById(String id) {
        return productRepository.findById(Long.valueOf(id)).orElse(null);
    }
}
