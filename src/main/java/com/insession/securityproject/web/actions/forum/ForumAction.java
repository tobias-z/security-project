package com.insession.securityproject.web.actions.forum;

import com.insession.securityproject.api.services.TopicService;
import com.insession.securityproject.domain.topic.ITopicService;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.repositories.TopicRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class ForumAction extends RootServlet {
    protected static final ITopicService topicService = new TopicService(
            new TopicRepository(DBConnection.getEmf())
    );

    private Integer[] getRequestNumbers(HttpServletRequest req, String ...paramNames) {
        try {
            Integer[] numbers = new Integer[paramNames.length];
            for (int i = 0; i < paramNames.length; i++) {
                numbers[i] = Integer.parseInt(req.getParameter(paramNames[i]));
            }
            return numbers;
        } catch (NumberFormatException e) {
            return new Integer[0];
        }
    }

    protected String createMutation(HttpServletRequest req, HttpServletResponse resp, String[] requestParams, Mutation action) throws ServletException, IOException {
        Integer[] numbers = getRequestNumbers(req, requestParams);
        if (numbers.length == 0)
            return sendError(req, resp, "Invalid arguments were provided");
        HttpSession session = req.getSession();
        String username = getUserName(session);

        return action.mutate(numbers, username, session);
    }

    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return super.sendError(req, resp, "This is not a route to be used as a page");
    }
}

@FunctionalInterface
interface Mutation {
    String mutate(Integer[] numbers, String username, HttpSession session) throws ServletException, IOException;
}
