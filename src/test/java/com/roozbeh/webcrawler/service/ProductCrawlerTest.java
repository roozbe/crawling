package com.roozbeh.webcrawler.service;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
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
    void fetchTopLevelUrls() throws IOException {
        doReturn(anyMainDocument()).when(productCrawler).getDocumentFromUrl(anyString());

        List<String> topLevelUrls = productCrawler.fetchTopLevelUrls();

        assertNotEquals(0, topLevelUrls.size());
    }

    @Test
    void fetchAllMidLevelUrls() throws IOException {
        doReturn(anyMidLevelDocument(), anyPageDocument()).when(productCrawler).getDocumentFromUrl(anyString());

        List<String> topLevelUrls = productCrawler.fetchAllMidLevelUrls(anyMidLevelUrlList());

        assertNotEquals(0, topLevelUrls.size());
    }

    @Test
    void fetchPageProductUrls() throws IOException {
        doReturn(anyPageDocument()).when(productCrawler).getDocumentFromUrl(anyString());

        List<String> urlList = productCrawler.fetchPageProductUrls(anyValidUrl());

        assertNotNull(urlList);
    }

    @Test
    void fetchElementsOfProduct() throws IOException {
        doReturn(anyProductDocument()).when(productCrawler).getDocumentFromUrl(anyString());

        Elements productElement = productCrawler.fetchElementsOfProduct(anyValidUrl());

        assertNotNull(productElement);
    }

    @Test
    void fetchNameOfProduct() throws IOException {
        String name = productCrawler.fetchNameOfProduct(anyValidProductElements());

        assertEquals(anyValidName(), name);
    }

    @Test
    void fetchPriceOfProduct() throws IOException {
        String price = productCrawler.fetchPriceOfProduct(anyValidProductElements());

        assertEquals(anyValidPrice(), price);
    }

    @Test
    void fetchDescriptionOfProduct() throws IOException {
        String description = productCrawler.fetchDescriptionOfProduct(anyValidProductElements());

        assertEquals(anyValidDescription(), description);
    }

    @Test
    void fetchExtraInfoOfProduct() throws IOException {
        Map<String, String> extraInfoOfProduct = productCrawler.fetchExtraInfoOfProduct(anyValidProductElements());

        assertEquals(anyFetchedExtraInfo(), extraInfoOfProduct);
    }
}