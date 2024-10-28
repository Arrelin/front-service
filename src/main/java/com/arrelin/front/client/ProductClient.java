package com.arrelin.front.client;

import com.arrelin.front.entity.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange("/api/product")
public interface ProductClient {


    @GetExchange("{id}")
    Product getProductById(@PathVariable Long id);

    @GetExchange("")
    List<Product> getAllProducts();

    @PostExchange("")
    void saveProduct(@RequestBody Product product);


    @DeleteExchange("/{id}")
    void deleteProduct(@PathVariable Long id);
}
