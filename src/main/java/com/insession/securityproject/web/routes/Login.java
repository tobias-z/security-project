package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.BruteForceService;
import com.insession.securityproject.api.services.NullValidatorService;
import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.*;
import com.insession.securityproject.infrastructure.DBConnection;
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
            new UserRepository(DBConnection.getEmf())
    );
    private final BruteForceService bruteForceService = new BruteForceService(10, 3, 3600);

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
            NullValidatorService.nullOrEmpty(userName, "userName", InvalidKeysException.class);
            NullValidatorService.nullOrEmpty(password, "password", InvalidKeysException.class);
            String ip = req.getRemoteAddr();
            bruteForceService.handleBruteForce(ip, userName);
            User user = userService.login(userName, password);
            userService.sendPinMail(user);
            session.setAttribute("pinCodeUsername", user.getUsername());
            session.removeAttribute("loginError");

            bruteForceService.resetUser(ip);
            return "/pin/email";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.setAttribute("loginError", e.getMessage());
            return "/login";
        }
    }
}
