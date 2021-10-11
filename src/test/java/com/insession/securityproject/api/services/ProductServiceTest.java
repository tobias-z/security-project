package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.product.IProductService;
import com.insession.securityproject.domain.product.Product;
import com.insession.securityproject.infrastructure.listbased.ListProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private IProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(new ListProjectRepository());
    }

    @Test
    void getAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
    }
}