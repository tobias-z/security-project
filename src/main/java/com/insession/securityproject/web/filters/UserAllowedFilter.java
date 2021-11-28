package com.insession.securityproject.web.filters;

import com.insession.securityproject.domain.user.UserRole;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAllowedFilter {
    private final UserRole[] rolesAllowed;
    private final HttpServletRequest req;
    private final HttpServletResponse res;

    public UserAllowedFilter(UserRole[] roleAllowed, HttpServletRequest req,
                             HttpServletResponse res) {
        this.rolesAllowed = roleAllowed;
        this.req = req;
        this.res = res;
    }

    public boolean doFilter()
            throws IOException, ServletException {
        HttpSession session = req.getSession();

        UserRole userRole = (UserRole) session.getAttribute("role");

        if (userRole == null) {
            return sendError("You are not allowed here");
        }

        if (rolesAllowed.length == 0)
            return true;

        if (!Arrays.asList(rolesAllowed).contains(userRole))
            return sendError("You are not allowed on that page");

        return true;
    }

    private boolean sendError(String message) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("errorCode", 200);
        session.setAttribute("errorMessage", message);
        return false;
    }

}
