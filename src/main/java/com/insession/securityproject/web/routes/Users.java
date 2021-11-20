package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



import com.insession.securityproject.api.services.UserService;
        import com.insession.securityproject.domain.user.*;
        import com.insession.securityproject.infrastructure.DBConnection;
        import com.insession.securityproject.infrastructure.cache.Redis;
        import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
        import com.insession.securityproject.infrastructure.repositories.UserRepository;
        import com.insession.securityproject.web.RootServlet;


        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;
        import java.io.IOException;

        import static com.insession.securityproject.domain.user.Whitelist.validateEmail;
        import static com.insession.securityproject.domain.user.Whitelist.validateInput;


@WebServlet("/users")
public class Users extends RootServlet {

    private static final IUserService userService = new UserService(new UserRepository(DBConnection.getEmf()));

    @Override
    public void init() throws ServletException {
        this.title = "Users";
        this.description = "Show users";
        this.setRolesAllowed(UserRole.ADMIN);
    }

    @Override
    public String loader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Loader");
        try {
            request.setAttribute("users", userService.getAll());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return "/users";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        try {
            String usertodelete = req.getParameter("usertodelete");
            System.out.println("Delete:"+usertodelete);
            String usertoedit = req.getParameter("usertoedit");
            System.out.println("Edit:"+usertoedit);
            if (usertodelete!=null){
                userService.deleteUserByUserName(usertodelete);
            }
            return "/users";
        } catch (Exception e) {
            session.setAttribute("signupError", e.getMessage());
            System.out.println("catch");
            return "/users";
        }
    }
}