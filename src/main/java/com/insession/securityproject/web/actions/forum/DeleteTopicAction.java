package com.insession.securityproject.web.actions.forum;

import com.insession.securityproject.domain.topic.NoTopicsFoundException;
import com.insession.securityproject.domain.user.InvalidKeysException;
import com.insession.securityproject.domain.user.InvalidUserException;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.domain.user.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/actions/delete-topic")
public class DeleteTopicAction extends ForumAction {

    @Override
    public void init() throws ServletException {
        this.setRolesAllowed(UserRole.USER, UserRole.ADMIN);
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] requestParams = new String[] {"topicId"};
        return createMutation(req, resp, requestParams, (numbers, username, session) -> {
            try {
                topicService.deleteTopic(numbers[0], username);
                req.setAttribute("topicRemoved", "Successfully deleted topic");
                return "/forum";
            } catch (InvalidKeysException | UserNotFoundException | NoTopicsFoundException | InvalidUserException e) {
                return sendError(req, resp, e.getMessage());
            }
        });
    }

}
