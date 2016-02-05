package com.normanhoeller.twitterydoo;

/**
 * Created by norman on 04/02/16.
 */
public interface MainPresenter {

    boolean checkIfCredentialsAreReady();
    void getAccessTokenFromTwitter();
    void storeAccessTokenInSharedPrefs(String accessToken);
    void navigateToPictureActivity(String searchQuery);

}
