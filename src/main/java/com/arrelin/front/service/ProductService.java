package com.arrelin.front.service;

import com.arrelin.front.client.ProductClient;
import com.arrelin.front.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductClient productClient;

    public List<Product> getAllProducts() {
        return productClient.getAllProducts();
    }

    public void saveProduct(Product product) {
        productClient.saveProduct(product);
    }


    public void deleteProduct(Long id) {
        productClient.deleteProduct(id);
    }
}