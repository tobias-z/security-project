package com.insession.securityproject.infrastructure.listbased;

import com.insession.securityproject.domain.product.IProductRepository;
import com.insession.securityproject.domain.product.Product;

import java.util.Arrays;
import java.util.List;

public class ListProjectRepository implements IProductRepository {
    private static ListProjectRepository instance;

    // Singleton used to always have the same items
    public static ListProjectRepository getInstance() {
        if (instance == null)
            instance = new ListProjectRepository();
        return instance;
    }

    private final List<Product> products;

    private ListProjectRepository() {
        products = initializeProducts();
    }

    private List<Product> initializeProducts() {
        return Arrays.asList(
                new Product("A", "Something"),
                new Product("B", "Something else"),
                new Product("C", "Yay"),
                new Product("Bob", "The builder")
        );
    }


    @Override
    public List<Product> getAllProducts() {
        return products;
    }
}
