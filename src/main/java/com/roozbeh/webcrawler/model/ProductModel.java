package com.roozbeh.webcrawler.model;

import lombok.*;

import java.util.Map;

/**
 * @author rezaeian
 */
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class ProductModel {

    private String name;
    private String price;
    private String description;
    private Map<String, String> extraInfo;
}
