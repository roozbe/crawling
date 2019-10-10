package com.roozbeh.webcrawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roozbeh.webcrawler.dal.entity.Product;
import com.roozbeh.webcrawler.model.ProductModel;
import com.roozbeh.webcrawler.model.UrlModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotherObject {


    public static final String DUMMY = "dummy";

    public static ProductModel anyValidProductModel() {
        return ProductModel.builder()
                .name(DUMMY)
                .description(DUMMY)
                .extraInfo(anyValidExtraInfo())
                .price(DUMMY)
                .build();
    }

    private static Map<String, String> anyValidExtraInfo() {
        Map<String, String> extraInfo = new HashMap<>();
        extraInfo.put(DUMMY, DUMMY);
        return extraInfo;
    }

    public static String toJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }

    public static int anyValidId() {
        return 1;
    }

    public static int anyInvalidId() {
        return 10;
    }

    public static Product anyValidPersistenceProduct() {
        Product product = new Product();
        product.setDescription(DUMMY);
        product.setId((long) anyValidId());
        product.setExtraInfo(anyValidExtraInfo());
        product.setPrice(DUMMY);
        product.setName(DUMMY);
        return product;
    }

    public static Product anyNonePersistenceProduct(){
        Product product = anyValidPersistenceProduct();
        product.setId(null);
        return product;
    }

    public static UrlModel anyValidUrlModel(){
        return new UrlModel(DUMMY);
    }

    public static List<String> anyValidUrlList(){
        List<String> urlModelList = new ArrayList<>();
        urlModelList.add(DUMMY);
        return urlModelList;
    }
}
