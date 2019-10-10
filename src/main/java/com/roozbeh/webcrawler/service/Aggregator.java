package com.roozbeh.webcrawler.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.roozbeh.webcrawler.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rezaeian
 */
@Service
@AllArgsConstructor
@Slf4j
public class Aggregator {

    private static final int EXPECTED_INSERTIONS = 10000;
    private static final double FPP = 0.01;
    private ProductCrawler productCrawler;
    private ProductService productService;

    public List<String> findAllProductUrls() throws IOException {
        List<String> topLevelUrls = productCrawler.fetchTopLevelUrls();
        List<String> pageUrls = productCrawler.fetchAllMidLevelUrls(topLevelUrls);
        List<String> productUrls = new ArrayList<>();
        for (String pageUrl : pageUrls) {
            List<String> fetchPageProductUrls = productCrawler.fetchPageProductUrls(pageUrl);
            productUrls.addAll(fetchPageProductUrls);
        }
        return productUrls;
    }

    public ProductModel fetchProductDetailsByItsUrl(String productUrl) throws IOException {
        Elements elements = productCrawler.fetchElementsOfProduct(productUrl);
        return ProductModel.builder()
                .name(productCrawler.fetchNameOfProduct(elements))
                .description(productCrawler.fetchDescriptionOfProduct(elements))
                .price(productCrawler.fetchPriceOfProduct(elements))
                .extraInfo(productCrawler.fetchExtraInfoOfProduct(elements))
                .build();
    }

    @Async
    public void starPersistingAllProductsToDb() throws IOException {
        log.info("Fetching and Persisting All Products started.");
        List<String> allProductUrls = findAllProductUrls();
        // Checking existence of product name
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), EXPECTED_INSERTIONS, FPP);
        for (String url : allProductUrls) {
            log.info("Product URL : {}", url);
            ProductModel productModel = fetchProductDetailsByItsUrl(url);
            log.info("Product : {}", productModel);
            if (!bloomFilter.mightContain(productModel.getName())) {
                log.info("Product does not exist");
                productService.save(productModel);
                bloomFilter.put(productModel.getName());
            } else {
                log.info("Product exists");
            }
        }
    }
}
