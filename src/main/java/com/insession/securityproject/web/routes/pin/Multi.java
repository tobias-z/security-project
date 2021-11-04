package com.insession.securityproject.web.routes.pin;

import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pin/multi")
public class Multi extends RootServlet {

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/pin/multi";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
