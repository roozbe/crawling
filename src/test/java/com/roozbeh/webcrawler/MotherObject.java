package com.roozbeh.webcrawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roozbeh.webcrawler.dal.entity.Product;
import com.roozbeh.webcrawler.model.ProductModel;
import com.roozbeh.webcrawler.model.UrlModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.roozbeh.webcrawler.constant.Constants.DIV_COLUMN_MAIN_CLASS;

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

    public static Document anyMainDocument() throws IOException {
        File in = new File("src/test/resources/MainDummy.html");
        return Jsoup.parse(in, null);
    }

    public static Document anyMidLevelDocument() throws IOException {
        File in = new File("src/test/resources/MidLevelDummy.html");
        return Jsoup.parse(in, null);
    }

    public static Document anyProductDocument() throws IOException {
        File in = new File("src/test/resources/ProductDummy.html");
        return Jsoup.parse(in, null);
    }

    public static Document anyPageDocument() throws IOException {
        File in = new File("src/test/resources/PageDummy.html");
        return Jsoup.parse(in, null);
    }

    public static List<String> anyMidLevelUrlList(){
        List<String> urlList = new ArrayList<>();
        urlList.add(DUMMY);
        return urlList;
    }

    public static Elements anyValidProductElements() throws IOException {
        File in = new File("src/test/resources/ProductDummy.html");
        Document doc = Jsoup.parse(in, null);
        return doc.select(DIV_COLUMN_MAIN_CLASS);
    }

    public static String anyValidDescription(){
        return "Black is back — was it ever gone? — which means all your favorite tops will get along with the comfortable and versatile Bardot Capri.\n" +
                " Black capris with pink waistband. Cropped leggings. Waistband drawcord.  Flat, thin and flattering. Made with organic fabric.";
    }

    public static String anyValidPrice(){
        return "$48.00";
    }

    public static String anyValidName(){
        return "Bardot Capri";
    }

    public static Map<String,String> anyFetchedExtraInfo(){
        Map<String,String> extraInfo = new HashMap<>();
        extraInfo.put("Style","Capri, Leggings");
        extraInfo.put("Material","Microfiber, Rayon, Spandex");
        extraInfo.put("Pattern","Color-Blocked");
        extraInfo.put("Climate","Indoor, Mild, Warm, Hot");
        return extraInfo;
    }

    public static String anyValidUrl(){
        return DUMMY;
    }
}
