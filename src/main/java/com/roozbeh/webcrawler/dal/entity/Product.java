package com.roozbeh.webcrawler.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

/**
 * @author rezaeian
 */
@Entity
@Getter
@Setter
@Table(name = "product")
@SequenceGenerator(name = "sq_product", sequenceName = "sq_product", allocationSize = 1)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_product")
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "PRODUCT_PRICE")
    private String price;

    @Lob
    @Column(name = "PRODUCT_DESCRIPTION")
    private String description;

    @ElementCollection
    @CollectionTable(name = "PRODUCT_EXTRA_INFO", joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")})
    @MapKeyColumn(name = "ITEM_NAME")
    @Column(name = "ITEM_DETAIL")
    private Map<String, String> extraInfo;
}
