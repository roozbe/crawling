package com.roozbeh.webcrawler.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author rezaeian
 */
@Getter
@Setter
@ToString
@Builder
public class ProductModel {

    private String name;
    private String price;
    private String description;
    private Map<String, String> extraInfo;
}
