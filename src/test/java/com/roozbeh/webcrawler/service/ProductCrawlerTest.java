package com.roozbeh.webcrawler.service;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.roozbeh.webcrawler.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class ProductCrawlerTest {

    @Spy
    @InjectMocks
    private ProductCrawler productCrawler;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(productCrawler, "mainUrl", "dummy");
    }

    @Test
    @DisplayName("given fetchTopLevelUrls when getDocumentFromUrl called then return topLevelList")
    void given_fetchTopLevelUrls_when_getDocumentFromUrl_called_then_return_topLevelList() throws IOException {
        doReturn(anyMainDocument()).when(productCrawler).getDocumentFromUrl(anyString());

        List<String> topLevelUrls = productCrawler.fetchTopLevelUrls();

        assertNotEquals(0, topLevelUrls.size());
    }

    @Test
    @DisplayName("given fetchAllMidLevelUrls when getDocumentFromUrl called then return midLevelUrlList")
    void given_fetchAllMidLevelUrls_when_getDocumentFromUrl_called_then_return_midLevelUrlList() throws IOException {
        doReturn(anyMidLevelDocument(), anyPageDocument()).when(productCrawler).getDocumentFromUrl(anyString());

        List<String> midLevelUrls = productCrawler.fetchAllMidLevelUrls(anyMidLevelUrlList());

        assertNotEquals(0, midLevelUrls.size());
    }

    @Test
    @DisplayName("given fetchPageProductUrls when getDocumentFromUrl called then return productUrlList")
    void given_fetchPageProductUrls_when_getDocumentFromUrl_called_then_return_productUrlList() throws IOException {
        doReturn(anyPageDocument()).when(productCrawler).getDocumentFromUrl(anyString());

        List<String> productUrlList = productCrawler.fetchPageProductUrls(anyValidUrl());

        assertNotNull(productUrlList);
    }

    @Test
    @DisplayName("given fetchElementsOfProduct when getDocumentFromUrl called then return productElement")
    void given_fetchElementsOfProduct_when_getDocumentFromUrl_called_then_return_productElement() throws IOException {
        doReturn(anyProductDocument()).when(productCrawler).getDocumentFromUrl(anyString());

        Elements productElement = productCrawler.fetchElementsOfProduct(anyValidUrl());

        assertNotNull(productElement);
    }

    @Test
    @DisplayName("given fetchNameOfProduct when productElement is valid then return product name")
    void given_fetchNameOfProduct_when_productElement_is_valid_then_product_name() throws IOException {
        String name = productCrawler.fetchNameOfProduct(anyValidProductElements());

        assertEquals(anyValidName(), name);
    }

    @Test
    @DisplayName("given fetchPriceOfProduct when productElement is valid then return product price")
    void given_fetchPriceOfProduct_when_productElement_is_valid_then_return_product_price() throws IOException {
        String price = productCrawler.fetchPriceOfProduct(anyValidProductElements());

        assertEquals(anyValidPrice(), price);
    }

    @Test
    @DisplayName("given fetchDescriptionOfProduct when productElement is valid then return product description")
    void given_fetchDescriptionOfProduct_when_productElement_is_valid_then_return_product_description() throws IOException {
        String description = productCrawler.fetchDescriptionOfProduct(anyValidProductElements());

        assertEquals(anyValidDescription(), description);
    }

    @Test
    @DisplayName("given fetchExtraInfoOfProduct when productElement is valid then return product extraInfo")
    void given_fetchExtraInfoOfProduct_when_productElement_is_valid_then_return_product_extraInfo() throws IOException {
        Map<String, String> extraInfoOfProduct = productCrawler.fetchExtraInfoOfProduct(anyValidProductElements());

        assertEquals(anyFetchedExtraInfo(), extraInfoOfProduct);
    }
}