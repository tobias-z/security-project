package com.insession.securityproject.web.actions.forum;

import com.insession.securityproject.domain.comment.CommentNotFoundException;
import com.insession.securityproject.domain.user.InvalidKeysException;
import com.insession.securityproject.domain.user.InvalidUserException;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.domain.user.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/actions/delete-comment")
public class DeleteCommentAction extends ForumAction {

    @Override
    public void init() throws ServletException {
        this.setRolesAllowed(UserRole.USER, UserRole.ADMIN);
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] requestParams = new String[] {"commentId", "topicId"};
        return createMutation(req, resp, requestParams, ((numbers, username, session) -> {
            try {
                int commentId = numbers[0];
                int topicId = numbers[1];
                topicService.deleteComment(commentId, username);
                req.setAttribute("commentRemoved", "Successfully deleted comment");
                return "/forum/" + topicId;
            } catch (InvalidKeysException | UserNotFoundException | InvalidUserException | CommentNotFoundException e) {
                return sendError(req, e.getMessage());
            }
        }));
    }

}
