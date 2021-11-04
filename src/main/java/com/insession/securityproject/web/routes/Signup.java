package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.*;
import com.insession.securityproject.infrastructure.Connection;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signup")
public class Signup extends RootServlet {

    private static final IUserService userService = new UserService(new UserRepository(Connection.getEmf()));

    @Override
    public void init() throws ServletException {
        this.title = "Sign up";
        this.description = "Create a new user";
        this.setRolesAllowed(UserRole.NO_USER);
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/signup";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        Integer phone = Integer.valueOf(req.getParameter("phone"));
        String password = req.getParameter("password");
        HttpSession session = req.getSession(true);
        try {
            User user = userService.signup(username, email, phone, password);
            return "/signup";
        } catch (UserCreationException | InvalidKeysException e) {
            session.setAttribute("signupError", e.getMessage());
            return "/signup";
        }
    }
}
