package com.roozbeh.webcrawler.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

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
        assertNotNull(productCrawler.fetchPageProductUrls(anyValidUrl()));
    }

    @Test
    void fetchElementsOfProduct() throws IOException {
        doReturn(anyProductDocument()).when(productCrawler).getDocumentFromUrl(anyString());
        assertNotNull(productCrawler.fetchElementsOfProduct(anyValidUrl()));
    }

    @Test
    void fetchNameOfProduct() throws IOException {
        assertEquals(anyValidName(), productCrawler.fetchNameOfProduct(anyValidProductElements()));
    }

    @Test
    void fetchPriceOfProduct() throws IOException {
        assertEquals(anyValidPrice(), productCrawler.fetchPriceOfProduct(anyValidProductElements()));
    }

    @Test
    void fetchDescriptionOfProduct() throws IOException {
        assertEquals(anyValidDescription(), productCrawler.fetchDescriptionOfProduct(anyValidProductElements()));
    }

    @Test
    void fetchExtraInfoOfProduct() throws IOException {
        assertEquals(anyFetchedExtraInfo(), productCrawler.fetchExtraInfoOfProduct(anyValidProductElements()));
    }
}