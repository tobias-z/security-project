package com.insession.securityproject.web.routes;


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


@WebServlet("/edituser")
public class EditUser extends RootServlet {

    private static final IUserService userService = new UserService(new UserRepository(DBConnection.getEmf()));

    @Override
    public void init() throws ServletException {
        this.title = " Edit User";
        this.description = "Edit user";
        this.setRolesAllowed(UserRole.ADMIN);
    }

    @Override
    public String loader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String username= (String) session.getAttribute("usertoedit");

        try {
            User user=userService.getUserByUserName(username);
            request.setAttribute("usertoedit", user);
            session.removeAttribute("EditUserError");

        } catch (UserNotFoundException e) {
            session.setAttribute("EditUserError", e.getMessage());
        }
        return "/edituser";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        try {
            String userToEdit= (String) req.getSession(true).getAttribute("usertoedit");
            User originalUser=userService.getUserByUserName(userToEdit);
            String email = req.getParameter("email");
            int phone = getPhone(req);
            String roleString=req.getParameter("role");
            UserRole role=((roleString.equals("ADMIN")) ? UserRole.ADMIN : UserRole.USER);
            User editedUser=new User(userToEdit,role,email,phone);
            validate( email, phone);


            if(!originalUser.getUserEmail().equals( editedUser.getUserEmail())){
                if (userService.userExists("none",editedUser.getUserEmail())){
                    throw new UserExistsException("A user with this email already exists "+editedUser.getUserEmail());
                }
            }

            if ((originalUser.getUserRole()==UserRole.ADMIN)&&(editedUser.getUserRole()==UserRole.USER)){
                throw new InvalidKeysException("You are not allowed to change user status to USER");
            }
            session.setAttribute("editedUser",editedUser);
            String loggedInUser= (String) session.getAttribute("userName");
            session.setAttribute("pinCodeUsername", loggedInUser);

            User user=userService.getUserByUserName(loggedInUser);
            userService.sendPinMail(user);


            return "/pin/edit";
        } catch (InvalidKeysException | UserExistsException  | UserNotFoundException e) {
            session.setAttribute("EditUserError", e.getMessage());
            return "/edituser";
        }
    }

    private int getPhone(HttpServletRequest req) throws InvalidKeysException {
        InvalidKeysException invalidKeysException = new InvalidKeysException("Not a valid phone number");
        try {
            int phone = Integer.parseInt(req.getParameter("phone"));
            if (countOfNumber(phone) != 8)
                throw invalidKeysException;
            return phone;
        } catch (NumberFormatException e) {
            throw invalidKeysException;
        }
    }

    private int countOfNumber(int phone) {
        int count = 0;
        while (phone != 0) {
            phone = phone / 10;
            count++;
        }
        return count;
    }

    private void validate( String email, Integer phone) throws InvalidKeysException {
        String message = "Invalid input provided in field: ";

        if (!validateEmail(email))
            throw new InvalidKeysException(message + "Email");
        if (!validateInput(String.valueOf(phone)))
            throw new InvalidKeysException(message + "Phone Number");

    }
}
