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

import static com.insession.securityproject.domain.user.Whitelist.validateEmail;
import static com.insession.securityproject.domain.user.Whitelist.validateInput;

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
            validate(username, email, phone, email);
            if (!userService.userExists(username, email)) {
                throw new UserExistsException("A user with that username or email already exists");
            }
            User user = new User(username, UserRole.USER, email, phone);
            userService.sendPinMail(user);
            userService.sendPinSMS(user);
            return "/pin/multi";
        } catch (InvalidKeysException | UserExistsException e) {
            session.setAttribute("signupError", e.getMessage());
            return "/signup";
        }
    }

    private void validate(String username, String email, Integer phone, String password) throws InvalidKeysException {
        String message = "Invalid input provided in field: ";
        if (!validateInput(username))
            throw new InvalidKeysException(message + "Username");
        if (!validateEmail(email))
            throw new InvalidKeysException(message + "Email");
        if (!validateInput(String.valueOf(phone)))
            throw new InvalidKeysException(message + "Phone Number");
        if (!validateInput(password))
            throw new InvalidKeysException(message + "Password");
    }
}