package com.insession.securityproject.web.routes.forum;

import com.insession.securityproject.api.services.TopicService;
import com.insession.securityproject.domain.comment.InvalidCommentException;
import com.insession.securityproject.domain.topic.ITopicService;
import com.insession.securityproject.domain.topic.NoTopicsFoundException;
import com.insession.securityproject.domain.topic.Topic;
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

@WebServlet("/forum/*")
public class CommentSection extends RootServlet {
    private static final ITopicService topicService = new TopicService(
            new TopicRepository(DBConnection.getEmf())
    );

    @Override
    public void init() throws ServletException {
        this.title = "Comment section";
        this.description = "The comment section of a topic";
        this.setRolesAllowed(UserRole.USER);
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = getId(req);
            if (id == -1) return "/forum";
            Topic topic = topicService.getTopic(id);
            req.setAttribute("topic", topic);
            return "/forum/comment-section";
        } catch (NoTopicsFoundException e) {
            return super.sendError(req, resp, e.getMessage());
        }
    }

    private int getId(HttpServletRequest req) {
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo.equals("/")) return -1;

            String param = pathInfo.split("/")[1];
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getId(req);
        if (id == -1) return "/forum";

        HttpSession session = req.getSession();
        try {
            String username = getUserName(session);
            String comment = req.getParameter("comment");
            topicService.addCommentToTopic(comment, username, id);
            session.removeAttribute("commentError");
        } catch (UserNotFoundException | InvalidCommentException | NoTopicsFoundException e) {
            session.setAttribute("commentError", e.getMessage());
        }

        return "/forum/" + id;
    }

}
