package services.impl;

import dao.Impl.TweetDAOImpl;
import dao.Impl.UserDAOImpl;
import dao.TweetDAO;
import dao.UserDAO;
import model.Tweet;
import model.User;
import services.TweetManagementService;

import java.util.HashSet;
import java.util.Set;

public class TweetManagementServiceImpl implements TweetManagementService {

    private TweetDAO tweetDAO;
    private UserDAO userDAO;

    public TweetManagementServiceImpl()

    {
        this.tweetDAO = new TweetDAOImpl();
        this.userDAO = new UserDAOImpl();
    }


    @Override
    public void addTweet(String userLogin, String message) {
        tweetDAO.addTweet(userDAO.getUserByLogin(userLogin), message);
    }

    @Override
    public void updateTweet(Long tweetId, String message) {
        tweetDAO.updateTweet(tweetId, message);
    }

    @Override
    public void deleteTweet(Long id) {
        tweetDAO.deleteTweet(id);
    }

    @Override
    public Set<Tweet> getFollowedTweet(String userLogin) {

        Set<Tweet> tweets = new HashSet<>();
        Set<User> follows = userDAO.getFollows(userLogin);
       // User user = userDAO.getUserByLogin(userLogin);

        tweets.addAll(tweetDAO.getUserTweets(userLogin));
        follows.stream().forEach(user1 -> tweets.addAll(tweetDAO.getUserTweets(user1.getLogin())));

       // user.getFollows().stream().forEach(user1 -> tweets.addAll(tweetDAO.getUserTweets(user1.getLogin())));
        return tweets;


    }
}
