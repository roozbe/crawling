package com.roozbeh.webcrawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.roozbeh.webcrawler.constant.Constants.*;

/**
 * @author rezaeian
 */
@Component
public class ProductCrawler {

    @Value("${crawler.url}")
    private String mainUrl;

    List<String> fetchTopLevelUrls() throws IOException {
        Document doc = Jsoup.connect(mainUrl).get();
        Elements topLevelHeadLines = doc.getElementsByTag(A_TAG).select(LEVEL_TOP_CLASS_NAME);
        return getAbsUrlsFromElements(topLevelHeadLines);
    }

    List<String> fetchAllMidLevelUrls(List<String> topLevelUrls) throws IOException {
        List<String> midLevelUrls = new ArrayList<>();
        for (String midLevelUrl : topLevelUrls) {
            midLevelUrls.addAll(fetchFirstPageMidLevelUrls(midLevelUrl));
        }
        return midLevelUrls;
    }

    private List<String> fetchFirstPageMidLevelUrls(String url) throws IOException {
        List<String> midLevelUrls = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        Elements firstPageUrlElements = doc.select(ITEM_CLASS_NAME);
        for (Element headline : firstPageUrlElements) {
            String firstPageUrl = headline.childNode(0).absUrl(HREF_ATTRIBUTE);
            midLevelUrls.add(firstPageUrl);
            midLevelUrls.addAll(fetchPagingMidLevelUrls(firstPageUrl));
        }
        return midLevelUrls;
    }

    private List<String> fetchPagingMidLevelUrls(String firstPageUrl) throws IOException {
        Document doc = Jsoup.connect(firstPageUrl).get();
        List<String> midLevelPagesUrls = new ArrayList<>();
        if (doc.select(DIV_PAGES_CLASS).size() > 0) {
            Elements pageUrlElements = doc.select(DIV_PAGES_CLASS).get(0).getElementsByTag(A_TAG).select(PAGE_CLASS_NAME);
            midLevelPagesUrls.addAll(getAbsUrlsFromElements(pageUrlElements));
        }
        return midLevelPagesUrls;
    }

    List<String> fetchPageProductUrls(String pageUrl) throws IOException {
        Document doc = Jsoup.connect(pageUrl).get();
        Elements productUrlElements = doc.select(A_PRODUCT_CLASS);
        return getAbsUrlsFromElements(productUrlElements);
    }

    private List<String> getAbsUrlsFromElements(Elements elements) {
        return elements.stream().map(element -> element.absUrl(HREF_ATTRIBUTE)).collect(Collectors.toList());
    }

    Elements fetchElementsOfProduct(String productUrl) throws IOException {
        Document doc = Jsoup.connect(productUrl).get();
        return doc.select(DIV_COLUMN_MAIN_CLASS);
    }

    String fetchNameOfProduct(Elements elements) {
        return elements.select(SPAN_ITEMPROP_NAME_ATTRIBUTE).html();
    }

    String fetchPriceOfProduct(Elements elements) {
        return elements.select(SPAN_MAIN_PRICE_CLASS).html();
    }

    String fetchDescriptionOfProduct(Elements elements) {
        return elements.select(P_DESCRIPTION_CLASS)
                .toString()
                .replaceAll(P_OPEN_TAG, EMPTY_STRING)
                .replaceAll(P_CLOSE_TAG, EMPTY_STRING)
                .replaceAll(BR_TAG, EMPTY_STRING)
                .replaceAll(DOT_SYMBOLE, EMPTY_STRING);
    }

    Map<String, String> fetchExtraInfoOfProduct(Elements elements) {
        return elements.select(TBODY_TR_TAG)
                .stream()
                .collect(Collectors.toMap(
                        e -> e.select(TH_TAG).html(),
                        e -> e.select(TD_TAG).html()));
    }
}
