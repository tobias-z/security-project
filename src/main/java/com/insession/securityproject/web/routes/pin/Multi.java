package com.insession.securityproject.web.routes.pin;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.UserCreationException;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.cache.Redis;
import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/pin/multi")
public class Multi extends RootServlet {

    private static final IUserService userService = new UserService(new UserRepository(DBConnection.getEmf()));

    @Override
    public void init() throws ServletException {
        this.title = "Multi Pin code";
        this.description = "Authentication of pin codes sent to users email and sms";
        this.setRolesAllowed(UserRole.NO_USER);
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession(true).getAttribute("signupUsername") == null) {
            super.sendError(req, resp, "You do not currently have a pin code that needs validating");
        }
        return "/pin/multi";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int email = Integer.parseInt(req.getParameter("email"));
            int sms = Integer.parseInt(req.getParameter("sms"));
            HttpSession session = req.getSession(true);
            String username = (String) session.getAttribute("signupUsername");
            UserCredentials credentials = getUserCredentials(username, req, resp);
            userService.signup(credentials, email, sms);
            session.setAttribute("userName", username);
            session.setAttribute("role", UserRole.USER);
            session.removeAttribute("signupUsername");
            return "/";
        } catch (NumberFormatException | UserCreationException e) {
            req.setAttribute("error", e.getMessage());
            return "/pin/multi";
        }
    }

    private UserCredentials getUserCredentials(String username, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserCredentials credentials = Redis.get(username, UserCredentials.class);
        if (credentials == null)
            sendError(req, res, "You dont have any user to login to");
        return credentials;
    }
}
