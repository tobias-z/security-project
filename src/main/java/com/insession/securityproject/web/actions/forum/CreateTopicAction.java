package com.insession.securityproject.web.actions.forum;

import com.insession.securityproject.domain.topic.InvalidTopicException;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.domain.user.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/actions/create-topic")
public class CreateTopicAction extends ForumAction {

    @Override
    public void init() throws ServletException {
        this.setRolesAllowed(UserRole.USER);
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String topic = req.getParameter("topic");
            String username = getUserName(req.getSession());
            int topicId = topicService.createTopic(topic, username);
            req.setAttribute("createdTopic", "Topic was successfully created");
            return "/forum/" + topicId;
        } catch (InvalidTopicException | UserNotFoundException e) {
            req.setAttribute("topicError", e.getMessage());
            return "/forum";
        }
    }
}
