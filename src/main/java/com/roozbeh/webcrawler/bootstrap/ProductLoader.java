package com.roozbeh.webcrawler.bootstrap;

import com.roozbeh.webcrawler.service.Aggregator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author rezaeian
 */
@Component
@AllArgsConstructor
@Slf4j
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

    private Aggregator aggregator;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            aggregator.starPersistingAllProductsToDb();
        } catch (IOException e) {
            log.error("Error occurs on fetching all products.", e);
        }
        log.info("Be patient! It takes up a lot of time!");
    }
}
