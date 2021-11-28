package com.insession.securityproject.web.routes;

import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/404")
public class FourOhFour extends RootServlet {

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object errorCode = session.getAttribute("errorCode");
        Object errorMessage = session.getAttribute("errorMessage");
        if (errorCode != null && errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
            req.setAttribute("errorCode", errorCode);
            session.removeAttribute("errorMessage");
            session.removeAttribute("errorCode");
        }
        return "/404";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/404";
    }

}
