package services.impl;

import model.Tweet;

import java.util.Set;

public interface TweetManagementService {

    void addTweet(String userLogin,String message);

    void update

    void deleteTweet(Long id);

    Set<Tweet> getFollowedTweet(String userLogin);

}
