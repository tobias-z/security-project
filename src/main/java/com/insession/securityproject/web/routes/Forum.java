package com.insession.securityproject.web.routes;

import com.insession.securityproject.api.services.TopicService;
import com.insession.securityproject.domain.topic.ITopicService;
import com.insession.securityproject.domain.topic.InvalidTopicException;
import com.insession.securityproject.domain.topic.NoTopicsFoundException;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.repositories.TopicRepository;
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

    @Override
    public void init() throws ServletException {
        this.title = "The Forum";
        this.description = "Forum of this website. Relevant topics will be discussed";
        this.setRolesAllowed(UserRole.USER);
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("topics", topicService.getTopics());
        } catch (NoTopicsFoundException e) {
            req.setAttribute("noTopics", e.getMessage());
        }
        return "/forum";
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String topic = req.getParameter("topic");
            String username = getUserName(req.getSession());
            topicService.createTopic(topic, username);
            req.setAttribute("createdTopic", "Topic was successfully created");

            // TODO: Send to created forum
            return "/forum";
        } catch (InvalidTopicException | UserNotFoundException e) {
            req.setAttribute("topicError", e.getMessage());
            return "/forum";
        }
    }
}
