package com.insession.securityproject.web.routes.pin;

import com.google.gson.Gson;
import com.insession.securityproject.api.services.AuthPinCodeService;
import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.domain.user.pincode.PinCodeChannel;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.cache.Redis;
import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;
import redis.clients.jedis.Jedis;

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
        Integer email = Integer.parseInt(req.getParameter("email"));
        Integer sms = Integer.parseInt(req.getParameter("sms"));
        HttpSession session = req.getSession(true);
        String username = (String) session.getAttribute("signupUsername");
        AuthPinCodeService pinCodeService = AuthPinCodeService.getInstance();
        boolean isValidEmail = pinCodeService.isValidPinCode(username, PinCodeChannel.EMAIL, email);
        boolean isValidSMS = pinCodeService.isValidPinCode(username, PinCodeChannel.SMS, sms);
        if (!isValidEmail || !isValidSMS) {
            req.setAttribute("error", "Invalid Email or SMS pin code");
            return "/pin/multi";
        }
        UserCredentials credentials = getUserCredentials(username, req, resp);
        return "/";
    }

    private UserCredentials getUserCredentials(String username, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try (Jedis jedis = Redis.getJedis()) {
            String jsonString = jedis.get(username);
            if (jsonString == null)
                sendError(req, res, "You dont have any user to login to");
            return new Gson().fromJson(jsonString, UserCredentials.class);
        }
    }
}
