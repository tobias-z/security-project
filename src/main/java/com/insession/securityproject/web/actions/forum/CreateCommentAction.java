package com.insession.securityproject.web.actions.forum;

import com.insession.securityproject.domain.comment.InvalidCommentException;
import com.insession.securityproject.domain.topic.NoTopicsFoundException;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.domain.user.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/actions/create-comment")
public class CreateCommentAction extends ForumAction {

    @Override
    public void init() throws ServletException {
        this.setRolesAllowed(UserRole.USER);
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return createMutation(req, resp, new String[]{ "topicId" }, ((numbers, username, session) -> {
            int topicId = numbers[0];
            try {
                String comment = req.getParameter("comment");
                topicService.addCommentToTopic(comment, username, topicId);
                session.removeAttribute("commentError");
            } catch (UserNotFoundException | InvalidCommentException | NoTopicsFoundException e) {
                session.setAttribute("commentError", e.getMessage());
            }

            return "/forum/" + topicId;
        }));
    }

}
