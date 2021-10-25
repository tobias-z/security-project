package com.insession.securityproject.web;

import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.web.filters.UserAllowedFilter;
import com.insession.securityproject.web.widgets.Navbar;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class RootServlet extends HttpServlet implements IRoute {

    protected UserRole roleAllowed;
    protected String title;
    protected String description;
    protected String cacheControl;

    public RootServlet() {
        this.roleAllowed = null;
        this.title = "Base title if none was given";
        this.description = "Base title if none was given";
        this.cacheControl = null;
    }

    protected void sendError(HttpServletRequest req, HttpServletResponse res, String message) throws ServletException, IOException {
        req.setAttribute("errorCode", 200);
        req.setAttribute("errorMessage", message);
        req.getRequestDispatcher("/WEB-INF/routes/404.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        init();

        setRoleIfNotLoggedIN(req);
        new UserAllowedFilter(this.roleAllowed, req, resp).doFilter();

        String route = loader(req, resp);
        req.setAttribute("title", title);
        req.setAttribute("description", description);
        req.setAttribute("content", "/WEB-INF/routes" + route + ".jsp");
        req.setAttribute("navbarItems", new Navbar(req).getMenuItems());
        if (this.cacheControl != null) {
            resp.setHeader("cache-control", this.cacheControl);
        }
        req.getRequestDispatcher("/WEB-INF/root.jsp").forward(req, resp);
    }

    private void setRoleIfNotLoggedIN(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("role") == null) {
            session.setAttribute("role", UserRole.NO_USER);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        new UserAllowedFilter(this.roleAllowed, req, resp).doFilter();
        String redirectPath = action(req, resp);
        resp.sendRedirect(req.getContextPath() + redirectPath);
    }
}