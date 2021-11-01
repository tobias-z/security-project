package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.ProductService;
import com.insession.securityproject.domain.product.IProductService;
import com.insession.securityproject.infrastructure.listbased.ListProjectRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products")
public class Products extends RootServlet {
    private final IProductService productService = new ProductService(ListProjectRepository.getInstance());

    @Override
    public void init() {
        this.title = "Products";
        this.description = "This page contains all the products you can get";
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productService.getAllProducts());
        return "/products";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
