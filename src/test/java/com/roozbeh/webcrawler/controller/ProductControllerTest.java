package com.roozbeh.webcrawler.controller;

import com.roozbeh.webcrawler.model.ProductModel;
import com.roozbeh.webcrawler.service.Aggregator;
import com.roozbeh.webcrawler.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.roozbeh.webcrawler.MotherObject.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private Aggregator aggregator;

    @Test
    @DisplayName("given saveProduct when POST any valid ProductModel then persist the product and return with id")
    void saveProduct() throws Exception {
        doReturn(anyValidPersistenceProduct()).when(productService).findOrSave(any(ProductModel.class));

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(anyValidProductModel())))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id", equalTo(anyValidId())));
    }

    @Test
    @DisplayName("given fetchProductDetailsByItsUrl when POST the product ulr then fetch and return the detail of the product")
    void fetchProductDetailsByItsUrl() throws Exception {
        doReturn(anyValidProductModel()).when(aggregator).fetchProductDetailsByItsUrl(anyString());

        mockMvc.perform(post("/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(anyValidUrlModel())))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name", equalTo(DUMMY)));
    }

    @Test
    @DisplayName("given fetchProductById when GET with valid Id then return the product")
    void fetchProductById() throws Exception {
        doReturn(anyValidPersistenceProduct()).when(productService).findProductById(anyString());

        mockMvc.perform(get("/products/" + anyValidId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name", equalTo(DUMMY)));
    }

    @Test
    @DisplayName("given findAllProductUrls when GET urls then return all the product urls")
    void findAllProductUrls() throws Exception {
        doReturn(anyValidUrlList()).when(aggregator).findAllProductUrls();

        mockMvc.perform(get("/urls"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].url", equalTo(DUMMY)));
    }
}