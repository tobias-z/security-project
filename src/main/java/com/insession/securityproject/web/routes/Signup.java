package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.*;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.cache.Redis;
import com.insession.securityproject.infrastructure.cache.RedisConnection;
import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final IUserService userService = new UserService(new UserRepository(DBConnection.getEmf()));
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Override
    public void init() throws ServletException {
        this.title = "Sign up";
        this.description = "Create a new user";
        this.setRolesAllowed(UserRole.NO_USER);
    }

    @Override
    public String loader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/signup";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        try {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            int phone = getPhone(req);
            String password = req.getParameter("password");
            String repeatedPassword = req.getParameter("repeatPassword");
            validate(username, email, phone, password, repeatedPassword);
            if (userService.userExists(username, email)) {
                throw new UserExistsException("A user with that username or email already exists");
            }
            User user = new User(username, UserRole.USER, email, phone);
            userService.sendPinMail(user);
            userService.sendPinSMS(user);
            Redis.getConnection()
                    .put(username, new UserCredentials(username, password, email, phone))
                    .close();
            session.setAttribute("signupUsername", username);
            return "/pin/multi";
        } catch (InvalidKeysException | UserExistsException e) {
            session.setAttribute("signupError", e.getMessage());
            return "/signup";
        }
    }

    private int getPhone(HttpServletRequest req) throws InvalidKeysException {
        InvalidKeysException invalidKeysException = new InvalidKeysException("Not a valid phone number");
        try {
            int phone = Integer.parseInt(req.getParameter("phone"));
            if (countOfNumber(phone) != 8)
                throw invalidKeysException;
            return phone;
        } catch (NumberFormatException e) {
            throw invalidKeysException;
        }
    }

    private int countOfNumber(int phone) {
        int count = 0;
        while (phone != 0) {
            phone = phone / 10;
            count++;
        }
        return count;
    }

    private void validate(String username, String email, Integer phone, String password, String repeatedPassword) throws InvalidKeysException {
        String message = "Invalid input provided in field: ";
        if (!validateInput(username)) {
            //log
            logger.info("Wrong username input: " + username);
            throw new InvalidKeysException(message + "Username");
        }
        if (!validateEmail(email)) {
            //log
            logger.warn("Wrong email input: " + email);
            throw new InvalidKeysException(message + "Email");
        }
        if (!validateInput(String.valueOf(phone))) {
            //log
            logger.warn("Wrong phone input: " + phone);
            throw new InvalidKeysException(message + "Phone Number");
        }
        if (!validateInput(password)) {
            //log
            logger.warn("Wrong password input: " + password);
            throw new InvalidKeysException(message + "Password");
        }
        if (password.length() < 16)
            throw new InvalidKeysException("Password is too short... Please provide at least 16 characters");
        if (!password.equals(repeatedPassword)) {
            //log
            logger.warn("Wrong repeatedPassword input: " + repeatedPassword);
            throw new InvalidKeysException("The two passwords did not match");
        }

    }
}
