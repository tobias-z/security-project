package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.ProductService;
import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.infrastructure.listbased.ListProjectRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class Login extends RootServlet {

    private IUserService userService;

    public void init() {
        this.title = "Login";
        this.description = "Login page";
        //this.userService = new UserService()
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return null;
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return null;
    }
}
