package controllers.servlets;

import controllers.Utils.ServletsUtils;
import model.Tweet;
import services.TweetManagementService;
import services.impl.TweetManagementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet(name = "MessageServlet", value = "/messages")
public class MessageServlet extends HttpServlet {

    private TweetManagementService service;


    @Override
    public void init() throws ServletException {
       service = new TweetManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String currentUserLogin = ServletsUtils.getUserLoginFromSession(req);
        Set<Tweet> followedTweet = service.getFollowedTweet(currentUserLogin);
        req.setAttribute(ServletsUtils.FOLLOWED_TWEETS,followedTweet);
        req.getRequestDispatcher("/messages.jsp").forward(req,resp);

    }
}
