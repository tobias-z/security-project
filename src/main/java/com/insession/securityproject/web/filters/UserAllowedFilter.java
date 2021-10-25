package com.insession.securityproject.web.filters;

import com.insession.securityproject.domain.user.UserRole;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAllowedFilter {
    private final UserRole roleAllowed;
    private final HttpServletRequest req;
    private final HttpServletResponse res;

    public UserAllowedFilter(UserRole roleAllowed, HttpServletRequest req,
                             HttpServletResponse res) {
        this.roleAllowed = roleAllowed;
        this.req = req;
        this.res = res;
    }

    public void doFilter()
            throws IOException, ServletException {
        if (roleAllowed == null) {
            return;
        }

        HttpSession session = req.getSession();

        // This should be username not user
        UserRole userRole = (UserRole) session.getAttribute("role");

        if (userRole == null) {
            sendError("Please stop brute brute forcing us :)");
        }

        if (!userRole.equals(roleAllowed)) {
            sendError("You are not allowed on that page");
        }
    }

    private void sendError(String message) throws ServletException, IOException {
        req.setAttribute("errorCode", 200);
        req.setAttribute("errorMessage", message);
        req.getRequestDispatcher("/WEB-INF/routes/404.jsp").forward(req, res);
    }

}
