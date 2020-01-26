package controllers.servlets;

import controllers.Utils.ServletsUtils;
import services.TweetManagementService;
import services.impl.TweetManagementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddTweetServlet", value = "/addMessage")
public class AddTweetServlet extends HttpServlet {

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
        String userMessageLogin = ServletsUtils.getUserLoginFromSession(req);
        String message = req.getParameter(ServletsUtils.TWEET_MESSAGE);
        if(message == null){
            req.getRequestDispatcher("/messages.jsp").forward(req,resp);
            return;
        }
        service.addTweet(userMessageLogin,message);
        req.getRequestDispatcher("messages").forward(req,resp);
    }
}
