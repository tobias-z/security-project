package com.insession.securityproject.web.routes;

import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.web.RootServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("")
public class Index extends RootServlet {

    @Override
    public void init() {
        this.title = "Home page";
        this.description = "This is the home page";
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return "/index";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

}
