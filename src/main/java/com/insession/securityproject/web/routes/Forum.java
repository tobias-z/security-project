package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.TopicService;
import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.topic.ITopicService;
import com.insession.securityproject.domain.topic.InvalidTopicException;
import com.insession.securityproject.domain.topic.NoTopicsFoundException;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.domain.user.Whitelist;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.repositories.TopicRepository;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/forum")
public class Forum extends RootServlet {
    private static final ITopicService topicService = new TopicService(
            new TopicRepository(DBConnection.getEmf())
    );

    private static final IUserService userService = new UserService(new UserRepository(DBConnection.getEmf()));

    UserRepository repo = new UserRepository(DBConnection.getEmf());

    @Override
    public void init() throws ServletException {
        this.title = "The Forum";
        this.description = "Forum of this website. Relevant topics will be discussed";
        this.setRolesAllowed(UserRole.USER, UserRole.ADMIN);
    }


    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        try {
            req.setAttribute("topics", topicService.getTopics());

            if ( repo.getUserImageFile(getUserName(session)) == null ||
                    !Whitelist.validateImageFile(repo.getUserImageFile(getUserName(session)))
            ) {
                req.setAttribute("imagefile", "test.png");
            } else {
                req.setAttribute("imagefile", repo.getUserImageFile(getUserName(session)));
            }

        } catch (NoTopicsFoundException e) {
            req.setAttribute("noTopics", e.getMessage());
        }

        return "/forum";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/forum";
    }
}
