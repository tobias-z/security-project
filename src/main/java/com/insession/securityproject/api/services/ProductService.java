package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.product.IProductRepository;
import com.insession.securityproject.domain.product.IProductService;
import com.insession.securityproject.domain.product.Product;

import java.util.List;

public class ProductService implements IProductService {
    private final IProductRepository repository;

    public ProductService(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.getAllProducts();
    }
}
