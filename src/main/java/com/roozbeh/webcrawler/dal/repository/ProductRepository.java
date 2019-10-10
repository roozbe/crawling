package com.roozbeh.webcrawler.dal.repository;

import com.roozbeh.webcrawler.dal.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author rezaeian
 */
public interface ProductRepository extends CrudRepository<Product,Long> {
    Optional<Product> findByName(String name);
}
