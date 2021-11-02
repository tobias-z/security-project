package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.entities.Connection;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends RootServlet {

    private final IUserService userService = new UserService(
            new UserRepository(Connection.getEmf())
    );

    public void init() {
        this.title = "Login";
        this.description = "Login page";
        setRolesAllowed(UserRole.NO_USER);
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/login";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        try {
            User user = userService.login(userName, password);
            userService.sendPinMail(user);
            session.setAttribute("pinCodeUsername", user.getUsername());
            session.removeAttribute("loginError");
            return "/login/pin/email";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.setAttribute("loginError", e.getMessage());
            return "/login";
        }
    }
}
