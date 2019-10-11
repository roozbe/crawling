package com.roozbeh.webcrawler.service;

import com.roozbeh.webcrawler.dal.entity.Product;
import com.roozbeh.webcrawler.dal.repository.ProductRepository;
import com.roozbeh.webcrawler.mapper.ProductMapper;
import com.roozbeh.webcrawler.model.ProductModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.roozbeh.webcrawler.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;


    @Test
    @DisplayName("given findOrSave when the product exist then return the product")
    void given_findOrSave_when_the_product_exist_then_return_the_product() {
        doReturn(Optional.of(anyValidPersistenceProduct())).when(productRepository).findByName(anyString());

        Product product = productService.findOrSave(anyValidProductModel());

        assertEquals(anyValidPersistenceProduct(), product);
    }

    @Test
    @DisplayName("given findOrSave when the product does not exist then persists and returns the product")
    void given_findOrSave_when_the_product_does_not_exist_then_persists_and_returns_the_product() {
        doReturn(Optional.empty()).when(productRepository).findByName(anyString());
        doReturn(anyValidPersistenceProduct()).when(productRepository).save(any(Product.class));
        doReturn(anyNonePersistenceProduct()).when(productMapper).productModelToProduct(any(ProductModel.class));

        Product product = productService.findOrSave(anyValidProductModel());

        assertEquals(anyValidPersistenceProduct(), product);
    }

    @Test
    @DisplayName("given save when ProductModel is valid then persist the product")
    void save() {
        doReturn(anyNonePersistenceProduct()).when(productMapper).productModelToProduct(any(ProductModel.class));

        productService.save(anyValidProductModel());

        verify(productRepository, atLeastOnce()).save(any(Product.class));
    }

    @Test
    @DisplayName("given findProductById when Id is valid then return the related product")
    void given_findProductById_when_Id_is_valid_then_return_the_related_product() {
        doReturn(Optional.of(anyValidPersistenceProduct())).when(productRepository).findById(anyLong());

        Product product = productService.findProductById(String.valueOf(anyValidId()));

        assertEquals(anyValidPersistenceProduct(), product);
    }

    @Test
    @DisplayName("given findProductById when Id is not valid then return null")
    void given_findProductById_when_Id_is_not_valid_then_return_null() {
        doReturn(Optional.empty()).when(productRepository).findById(anyLong());

        Product product = productService.findProductById(String.valueOf(anyInvalidId()));

        assertNull(product);
    }
}