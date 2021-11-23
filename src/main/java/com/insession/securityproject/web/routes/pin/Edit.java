package com.insession.securityproject.web.routes.pin;


import com.insession.securityproject.api.services.AuthPinCodeService;
import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.domain.pincode.PinCodeChannel;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/pin/edit")
public class Edit extends RootServlet {

    private final IUserService userService = new UserService(
            new UserRepository(DBConnection.getEmf())
    );

    @Override
    public void init() throws ServletException {
        this.title = "Edit PinCode";
        this.description = "PinCode validation from email";
        setRolesAllowed(UserRole.ADMIN);
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("pinCodeUsername") == null) {
            super.sendError(req, resp, "You do not currently have a pin code that needs validating");
        }
        return "/pin/edit";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("pinCodeUsername");
            Integer pinCode = getPinCode(req);

            boolean isValidPinCode = new AuthPinCodeService().isValidPinCode(username, PinCodeChannel.EMAIL, pinCode);
            if (!isValidPinCode)
                return "/pin/edit";
            User editedUser= (User) session.getAttribute("editedUser");
            userService.edit(editedUser);
            return "/users";
        } catch (Exception e) {
            return super.sendError(req, resp, "Please try to edit again. An error happened when trying to find your user");
        }
    }


    private int getPinCode(HttpServletRequest req) {
        try {
            return Integer.parseInt(req.getParameter("pin-code"));
        } catch (NumberFormatException e) {
            return 1;
        }
    }

}
