package com.insession.securityproject.web.routes.login.pin;

import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login/pin/email")
public class Email extends RootServlet {

    @Override
    public void init() throws ServletException {
        this.title = "Email PinCode";
        this.description = "PinCode validation from email";
        this.roleAllowed = UserRole.NO_USER;
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("pinCodeUsername") == null) {
            super.sendError(req, resp, "You do not currently have a pin code that needs validating");
        }
        return "/login/pin/email";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

}
