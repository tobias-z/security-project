package com.insession.securityproject.web.routes;


import com.insession.securityproject.api.services.TopicService;
import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.topic.ITopicService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.Whitelist;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.repositories.TopicRepository;
import com.insession.securityproject.infrastructure.repositories.UserRepository;
import com.insession.securityproject.web.RootServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;


@WebServlet("/profile")
@MultipartConfig (
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 2,
        maxRequestSize = 1024 * 1024 * 2
)
public class Profile extends RootServlet {

    private static final ITopicService topicService = new TopicService(
            new TopicRepository(DBConnection.getEmf())
    );
    private static final IUserService userService = new UserService(new UserRepository(DBConnection.getEmf()));
    UserRepository repo = new UserRepository(DBConnection.getEmf());
    String userImage;


    @Override
    public String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        //String username = getUserName(session);

        if ( repo.getUserImageFile(getUserName(session)) == null ||
                !Whitelist.validateImageFile(repo.getUserImageFile(getUserName(session)))
        ) {
            req.setAttribute("imagefile", "test.png");
        } else {
            req.setAttribute("imagefile", repo.getUserImageFile(getUserName(session)));
        }
        return "/profile";

    }

    @Override
    public String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String username = getUserName(session);
        String userCheck = "_" + username + "_";

        ServletContext ctx= req.getServletContext();
        System.out.println("ctx" + ctx);

        // Get the absolute path of the file
        String path = ctx.getRealPath("images/");
        System.out.println("path: " + path);
        Part filePart = req.getPart("file");
        String fileName = userCheck + filePart.getSubmittedFileName();

        // Image filename extension validation
        boolean bool = Whitelist.validateImageFile(fileName);
        // Image header validation
        String mimeType = getServletContext().getMimeType(fileName);

        if (bool && mimeType.startsWith("image/")) {
            if (path.contains(username)) {
                System.out.println("path contains"); //Files.delete(Paths.get(path));
            }
            for (Part part : req.getParts()) {
                part.write(path +  fileName);
            }
            } else {
                System.out.println("Illegal file");
            }
            repo.createUserFile(username, fileName);

        return "/profile";
    }
}

/*

 if (path.contains("_" + username +"_")) {
                System.out.println("path contains"); //Files.delete(Paths.get(path));
            }
  try {
            String usernameImageToDelete = req.getParameter("imagetodelete");
            System.out.println("usernameImageToDelete: " + usernameImageToDelete);
            if (usernameImageToDelete != null) {
                userService.deleteImageFile(usernameImageToDelete);
                System.out.println("BOOM");
            }
            } catch(UserNotFoundException e){
                e.printStackTrace();
            }
              String usernameImageToDelete = req.getParameter("imagetodelete");
            System.out.println("usernameImageToDelete: " + usernameImageToDelete);
            if (usernameImageToDelete != null) {
                try {
                    repo.deleteImageFile(usernameImageToDelete);
                    System.out.println("BOOM");
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
            }
 */
