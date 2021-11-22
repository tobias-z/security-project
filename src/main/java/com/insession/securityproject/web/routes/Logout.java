package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



import com.insession.securityproject.api.services.UserService;
        import com.insession.securityproject.domain.user.IUserService;
        import com.insession.securityproject.domain.user.User;
        import com.insession.securityproject.domain.user.UserRole;
        import com.insession.securityproject.infrastructure.DBConnection;
        import com.insession.securityproject.infrastructure.repositories.UserRepository;
        import com.insession.securityproject.web.RootServlet;

        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;
        import java.io.IOException;

@WebServlet("/logout")
public class Logout extends RootServlet {

    private final IUserService userService = new UserService(
            new UserRepository(DBConnection.getEmf())
    );

    public void init() {
        this.title = "Logout";
        this.description = "Logout page";
        setRolesAllowed(UserRole.ADMIN,UserRole.USER);
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        return "/logout";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();


        return "/";
    }
}
