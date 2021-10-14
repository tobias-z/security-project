package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends RootServlet {

    private IUserService userService = new UserService(new UserRepository(Persistence.createEntityManagerFactory("pu")));

    public void init() {
        this.title = "Login";
        this.description = "Login page";
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/login";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        try {
            User user = userService.login(userName, password);
            HttpSession session = req.getSession();
            session.setAttribute("userName", user.getUsername());
            session.setAttribute("role", user.getUserRole());
        } catch (Exception e) {
            req.setAttribute("loginError", e.getMessage());
            return "/login";
        }
        return "/index";
    }
}
